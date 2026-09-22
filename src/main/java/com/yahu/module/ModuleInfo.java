package com.yahu.module;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();
    Category category();
    String description() default "";
    String[] aliases() default {};
    int keybind() default -1;
    boolean defaultEnabled() default false;
}