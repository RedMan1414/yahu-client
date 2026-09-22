package com.yahu.event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends Event>, List<Listener>> listeners = new ConcurrentHashMap<>();
    private final ExecutorService asyncExecutor = Executors.newVirtualThreadPerTaskExecutor();
    private final MethodHandles.Lookup lookup = MethodHandles.lookup();

    private EventBus() {}

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public <T extends Event> void subscribe(Class<T> eventClass, Object listener) {
        List<Method> methods = new ArrayList<>();
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubscribeEvent.class)) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1 && eventClass.isAssignableFrom(params[0])) {
                    methods.add(method);
                }
            }
        }
        if (methods.isEmpty()) return;

        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).addAll(methods.stream()
                .map(m -> new Listener(listener, m))
                .sorted(Comparator.comparingInt(l -> -l.priority.ordinal()))
                .toList());
    }

    public <T extends Event> void unsubscribe(Class<T> eventClass, Object listener) {
        List<Listener> list = listeners.get(eventClass);
        if (list != null) {
            list.removeIf(l -> l.target == listener);
        }
    }

    public <T extends Event> void post(T event) {
        List<Listener> list = listeners.get(event.getClass());
        if (list == null) return;

        for (Listener listener : list) {
            if (event.isCancelled() && listener.priority != EventPriority.HIGH) break;
            try {
                if (listener.async) {
                    asyncExecutor.submit(() -> invoke(listener, event));
                } else {
                    invoke(listener, event);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void invoke(Listener listener, Event event) {
        try {
            listener.handle.invoke(listener.target, event);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static class Listener {
        final Object target;
        final MethodHandle handle;
        final EventPriority priority;
        final boolean async;

        Listener(Object target, Method method) {
            this.target = target;
            this.priority = method.getAnnotation(SubscribeEvent.class).priority();
            this.async = method.getAnnotation(SubscribeEvent.class).async();
            try {
                this.handle = MethodHandles.lookup().unreflect(method);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdown() {
        asyncExecutor.shutdown();
    }
}