package com.github.osvaldopina.composedannotation.map;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;
import com.github.osvaldopina.composedannotation.utils.ConversionUtils;
import com.github.osvaldopina.composedannotation.utils.ReflectionUtils;

public class MethodMap {

	private Method annotationMethod;

	private Method builderMethod;

	private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;

	private ConversionUtils conversionUtils = ConversionUtils.INSTANCE;

	MethodMap(Method annotationMethod, Method builderMethod) {
		this.annotationMethod = annotationMethod;
		this.builderMethod = builderMethod;
	}

	public void transferFromAnnotationToBuilder(Annotation annotation,
												ComposedAnnotationPropertiesBuilder composedAnnotationPropertiesBuilder) {

		Object annotationValue = reflectionUtils.invoke(annotation, annotationMethod);

		reflectionUtils.invoke(composedAnnotationPropertiesBuilder, builderMethod,
				conversionUtils.convert(annotationValue, builderMethod.getParameterTypes()[0]));
	}

	public Method getAnnotationMethod() {
		return annotationMethod;
	}

	public Method getBuilderMethod() {
		return builderMethod;
	}
}
