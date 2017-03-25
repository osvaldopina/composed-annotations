package com.github.osvaldopina.composedannotation.discover;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.finder.ComposedAnnotationFinder;

public class ComposedAnnotationDiscover {

	public static final ComposedAnnotationDiscover INSTANCE = new ComposedAnnotationDiscover();

	private final ComposedAnnotationFinder composedAnnotationFinder = ComposedAnnotationFinder.INSTANCE;

	protected ComposedAnnotationDiscover() {
	}

	public Annotation getAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> baseAnnotationType) {
		return composedAnnotationFinder.getAnnotation(annotatedElement.getDeclaredAnnotations(), baseAnnotationType);
	}

	public Annotation getAnnotation(Method method, int paramterIndex, Class<? extends Annotation> baseAnnotationType) {
		return composedAnnotationFinder.getAnnotation(method.getParameterAnnotations()[paramterIndex], baseAnnotationType);
	}

}
