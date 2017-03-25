package com.github.osvaldopina.composedannotation.test.annotation;

public class BaseAnnotationProperties {

	private String prop1;

	private Class<?> prop2;

	protected BaseAnnotationProperties(String prop1, Class<?> prop2) {
		this.prop1 = prop1;
		this.prop2 = prop2;
	}

	public String getProp1() {
		return prop1;
	}

	public Class<?> getProp2() {
		return prop2;
	}

}
