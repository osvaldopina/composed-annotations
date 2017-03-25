package com.github.osvaldopina.composedannotation.configuration;

import java.lang.annotation.Annotation;

public class ComposedAnnotationConfiguration {

	private Class<? extends Annotation> baseAnnotation;

	private Class<? extends ComposedAnnotationPropertiesBuilder> builderType;

	public ComposedAnnotationConfiguration(Class<? extends Annotation> baseAnnotation,
										   Class<? extends ComposedAnnotationPropertiesBuilder> builderType) {
		assert  baseAnnotation != null : "baseAnnotation cannot be null";
		assert builderType != null : "builderType cannot be null";

		this.baseAnnotation = baseAnnotation;
		this.builderType = builderType;
	}

	public Class<? extends Annotation> getBaseAnnotationType() {
		return baseAnnotation;
	}

	public Class<? extends ComposedAnnotationPropertiesBuilder> getBuilderType() {
		return builderType;
	}

}
