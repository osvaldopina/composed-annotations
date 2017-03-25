package com.github.osvaldopina.composedannotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.properties.AnnotationPropertiesCreator;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfigurations;
import com.github.osvaldopina.composedannotation.discover.ComposedAnnotationCache;
import com.github.osvaldopina.composedannotation.discover.ComposedAnnotationDiscover;

public class ComposedAnnotationReader {

	private ComposedAnnotationCache composedAnnotationCache =
			new ComposedAnnotationCache(ComposedAnnotationDiscover.INSTANCE);

	private AnnotationPropertiesCreator annotationPropertiesCreator = AnnotationPropertiesCreator.INSTANCE;

	private ComposedAnnotationConfigurations configurations;

	public ComposedAnnotationReader(ComposedAnnotationConfigurations configurations) {
		this.configurations = configurations;
	}

	public boolean has(AnnotatedElement annotatedElement, Class<? extends Annotation> baseAnnotationType) {
		return composedAnnotationCache.getAnnotation(annotatedElement, baseAnnotationType) != null;
	}

	public <T> T read(AnnotatedElement annotatedElement, Class<? extends Annotation> baseAnnotationType,
					  Class<T> annotationPropertiesType) {

		Annotation composedAnnotation = composedAnnotationCache.getAnnotation(annotatedElement, baseAnnotationType);

		if (composedAnnotation == null) {
			return null;
		}
		return annotationPropertiesCreator.create(configurations, baseAnnotationType, annotationPropertiesType,
				composedAnnotation);
	}

	public boolean has(Method method, int parameterIndex, Class<? extends Annotation> baseAnnotationType) {
		return composedAnnotationCache.getAnnotation(method, parameterIndex, baseAnnotationType) != null;
	}

	public <T> T read(Method method, int parameterIndex, Class<? extends Annotation> baseAnnotationType,
					  Class<T> annotationPropertiesType) {

		Annotation composedAnnotation = composedAnnotationCache.getAnnotation(method, parameterIndex, baseAnnotationType);

		if (composedAnnotation == null) {
			return null;
		}
		return annotationPropertiesCreator.create(configurations, baseAnnotationType, annotationPropertiesType,
				composedAnnotation);
	}
}
