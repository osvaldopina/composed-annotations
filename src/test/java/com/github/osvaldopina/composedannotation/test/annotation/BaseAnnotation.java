package com.github.osvaldopina.composedannotation.test.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface BaseAnnotation {

	String prop1() default "";

	Class<?> prop2() default Object.class;

}
