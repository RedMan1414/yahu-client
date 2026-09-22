package com.yahu.event;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeEvent {
    EventPriority priority() default EventPriority.NORMAL;
    boolean async() default false;
}