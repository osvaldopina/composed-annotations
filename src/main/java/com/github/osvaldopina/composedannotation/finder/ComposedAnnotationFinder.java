package com.github.osvaldopina.composedannotation.finder;

import java.lang.annotation.Annotation;

public class ComposedAnnotationFinder {

	public static final ComposedAnnotationFinder INSTANCE = new ComposedAnnotationFinder();

	protected ComposedAnnotationFinder() {
	}

	public Annotation getAnnotation(Annotation[] elementAnnotations, Class<? extends Annotation> baseAnnotationType) {
		for (Annotation annotation : elementAnnotations) {
			if (annotation.annotationType().equals(baseAnnotationType)) {
				return annotation;
			}
			for (Annotation internalAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
				if (internalAnnotation.annotationType().equals(baseAnnotationType)) {
					return annotation;
				}
			}
		}
		return null;
	}
}