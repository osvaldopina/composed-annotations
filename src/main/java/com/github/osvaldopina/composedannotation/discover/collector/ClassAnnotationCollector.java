package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Proxy;
import java.util.*;

public class ClassAnnotationCollector implements AnnotationCollector {

	public static final ClassAnnotationCollector INSTANCE = new ClassAnnotationCollector();

	protected ClassAnnotationCollector() {

	}

	@Override
	public Set<Annotation> collectAll(AnnotatedElement annotatedElement) {
		return Collections.unmodifiableSet(collectAll((Class) annotatedElement, false));
	}

	@Override
	public boolean canBeAppliedTo(AnnotatedElement annotatedElement) {
		return annotatedElement instanceof Class;
	}

	private Set<Annotation> collectAll(Class<?> clazz, boolean includeOnlyInherited) {
		Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<Class<? extends Annotation>, Annotation>();

		addAll(annotations, filterAnnotations(Arrays.asList(clazz.getDeclaredAnnotations()), includeOnlyInherited));

		Class<?> superClass = clazz.getSuperclass();

		if (superClass != null && (!Proxy.class.equals(superClass))) {
			addAll(annotations, collectAll(superClass, true));
		}

		for (Class<?> interf : clazz.getInterfaces()) {
			addAll(annotations, collectAll(interf, !Proxy.isProxyClass(clazz)));
		}

		return new HashSet<Annotation>(annotations.values());
	}

	private void addAll(Map<Class<? extends Annotation>, Annotation> annotations, Collection<Annotation> annotationsToBeAdded) {
		for (Annotation annotationToBeAdded : annotationsToBeAdded) {
			if (!annotations.containsKey(annotationToBeAdded.annotationType())) {
				add(annotations, annotationToBeAdded);
			} else {
				add(annotations, annotationToBeAdded);
			}
		}
	}

	private void add(Map<Class<? extends Annotation>, Annotation> annotations, Annotation annotationsToBeAdded) {
		if (!annotations.containsKey(annotationsToBeAdded.annotationType())) {
			annotations.put(annotationsToBeAdded.annotationType(), annotationsToBeAdded);
		}
	}

	private Set<Annotation> filterAnnotations(Collection<Annotation> annotations, boolean onlyInherited) {
		Set<Annotation> inheritedAnnotations = new HashSet<>();

		for (Annotation annotation : annotations) {
			if (!onlyInherited || (onlyInherited && isInheritedAnnotated(annotation))) {
				inheritedAnnotations.add(annotation);
			}
		}

		return inheritedAnnotations;
	}

	private boolean isInheritedAnnotated(Annotation annotationToBeAdded) {
		if (annotationToBeAdded.annotationType().getName().startsWith("java.lang")) {
			return false;
		}
		if (annotationToBeAdded.annotationType().isAnnotationPresent(Inherited.class)) {
			return true;
		} else if (annotationToBeAdded.annotationType().getAnnotations().length == 0) {
			return false;
		} else {
			for (Annotation annotation : annotationToBeAdded.annotationType().getAnnotations()) {
				if (isInheritedAnnotated(annotation)) {
					return true;
				}
			}
			return false;
		}
	}
}
