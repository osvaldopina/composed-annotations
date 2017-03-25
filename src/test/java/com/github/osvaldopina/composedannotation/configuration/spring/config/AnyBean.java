package com.github.osvaldopina.composedannotation.configuration.spring.config;

import java.lang.annotation.*;

@AnyBean.MyAnnotation
public class AnyBean {

	public void aMethod() {

	}


	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	public static @interface MyAnnotation {

	}
}
