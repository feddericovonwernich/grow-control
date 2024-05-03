package com.fg.grow_control.service.assistant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionDefinition {

    String name();

    String description();

    String parameters();

}