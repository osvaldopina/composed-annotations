package com.github.osvaldopina.composedannotation.test.annotation;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;

public class BaseAnnotationBuilder implements ComposedAnnotationPropertiesBuilder {

	private String prop1;

	private Class<?> prop2;

	@Override
	public BaseAnnotationProperties build() {
		return new BaseAnnotationProperties(prop1, prop2);
	}

	public BaseAnnotationBuilder prop1(String prop1) {
		this.prop1 = prop1;
		return this;
	}

	public BaseAnnotationBuilder prop2(Class<?> prop2) {
		this.prop2 = prop2;
		return this;
	}

}
