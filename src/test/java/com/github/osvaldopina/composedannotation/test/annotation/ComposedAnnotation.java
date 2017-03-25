package com.github.osvaldopina.composedannotation.test.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BaseAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComposedAnnotation {

	AnyEnum prop1() default AnyEnum.VALUE1;

	Class<?> prop2() default Object.class;


}
