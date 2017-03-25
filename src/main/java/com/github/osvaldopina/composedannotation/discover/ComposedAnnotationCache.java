package com.github.osvaldopina.composedannotation.discover;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ComposedAnnotationCache {

    private ComposedAnnotationDiscover composedAnnotationDiscover;

	private Map<AnnotatedElement, Annotation> annotatedElementCache =
			new ConcurrentHashMap<AnnotatedElement, Annotation>();

	private Map<AnnotatedElement, Boolean> annotatedElementNulAnnoatationCache =
			new ConcurrentHashMap<AnnotatedElement, Boolean>();

	private Map<ParameterKey, Annotation> annotatedParameterCache =
			new ConcurrentHashMap<ParameterKey, Annotation>();

	private Map<ParameterKey, Boolean> annotatedParameterNulAnnoatationCache =
			new ConcurrentHashMap<ParameterKey, Boolean>();

	public ComposedAnnotationCache(ComposedAnnotationDiscover composedAnnotationDiscover) {
		this.composedAnnotationDiscover = composedAnnotationDiscover;

	}

	public Annotation getAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> baseAnnotationType) {

		if (annotatedElementNulAnnoatationCache.containsKey(annotatedElement)) {
			return null;
		}

		Annotation annotation = annotatedElementCache.get(annotatedElement);

		if (annotation == null) {
			annotation = composedAnnotationDiscover.getAnnotation(annotatedElement, baseAnnotationType);

			if (annotation == null) {
				annotatedElementNulAnnoatationCache.put(annotatedElement, Boolean.TRUE);
			}
			else {
				annotatedElementCache.put(annotatedElement, annotation);
			}
		}

		return annotation;
	}

	public Annotation getAnnotation(Method method, int paramterIndex, Class<? extends Annotation> baseAnnotationType) {
		ParameterKey parameterKey = new ParameterKey(method, paramterIndex);

		if (annotatedParameterNulAnnoatationCache.containsKey(parameterKey)) {
			return null;
		}

		Annotation annotation = annotatedParameterCache.get(parameterKey);

		if (annotation == null) {
			annotation = composedAnnotationDiscover.getAnnotation(method, paramterIndex, baseAnnotationType);

			if (annotation == null) {
				annotatedParameterNulAnnoatationCache.put(parameterKey, Boolean.TRUE);
			}
			else {
				annotatedParameterCache.put(parameterKey, annotation);
			}
		}

		return annotation;
	}

	private static class ParameterKey {
		private Method method;
		private int parameterIndex;

		private ParameterKey(Method method, int parameterIndex) {
			this.method = method;
			this.parameterIndex = parameterIndex;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ParameterKey that = (ParameterKey) o;

			if (parameterIndex != that.parameterIndex) return false;
			return method.equals(that.method);
		}

		@Override
		public int hashCode() {
			int result = method.hashCode();
			result = 31 * result + parameterIndex;
			return result;
		}
	}

}
