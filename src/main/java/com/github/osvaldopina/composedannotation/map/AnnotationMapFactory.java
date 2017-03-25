package com.github.osvaldopina.composedannotation.map;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.osvaldopina.composedannotation.ComposedAnnotationError;
import com.github.osvaldopina.composedannotation.utils.ReflectionUtils;

public class AnnotationMapFactory {

	public static final AnnotationMapFactory INSTANCE = new AnnotationMapFactory();


	private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;


	protected AnnotationMapFactory() {

	}

	public AnnotationMap create(Class<? extends Annotation> annotationType, Class<?> builderType) {

		List<MethodMap> methodMaps = new ArrayList<>();

		for (Method annotationMethod : annotationType.getDeclaredMethods()) {
			if (! reflectionUtils.isObjectMethod(annotationMethod.getName(), annotationMethod.getParameterTypes())) {
				Method builderMethod = reflectionUtils.getMethod(builderType, annotationMethod.getName(), annotationMethod.getReturnType());
				if (builderMethod == null) {
					builderMethod = reflectionUtils.getMethod(builderType, annotationMethod.getName(), String.class);
				}
				if (builderMethod != null) {
					methodMaps.add(new MethodMap(annotationMethod, builderMethod));
				}
				else {
					throw new ComposedAnnotationError("Could not find method " + annotationMethod.getName() + " with " +
					annotationMethod.getReturnType() + " or java.lang.String as parameter in builder " + builderType);
				}
			}
		}
		return new AnnotationMap(methodMaps);
	}
}