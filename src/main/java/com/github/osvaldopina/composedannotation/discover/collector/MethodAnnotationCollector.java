package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

import com.github.osvaldopina.composedannotation.annotation.MethodInherited;

public class MethodAnnotationCollector implements AnnotationCollector {

	private BridgedMethodFinder bridgedMethodFinder = BridgedMethodFinder.INSTANCE;

	@Override
	public Set<Annotation> collectAll(AnnotatedElement annotatedElement) {
		Set<Annotation> annotations = new HashSet<Annotation>();

		Method method = (Method) annotatedElement;

		annotations.addAll(collectAllFromMethod(method));

		Method correspondent = bridgedMethodFinder.getCorrespondent(method);

		if (correspondent != null) {
			annotations.addAll(collectAllFromMethod(correspondent));
		}

		return annotations;
	}

	@Override
	public boolean canBeAppliedTo(AnnotatedElement annotatedElement) {
		return annotatedElement instanceof Method;
	}

	private Method getFromClass(Class<?> clazz, Method original) {
		try {
			return clazz.getMethod(original.getName(), original.getParameterTypes());
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	private Set<Annotation> collectAllFromMethod(Method method) {
		Set<Annotation> annotations = new HashSet<Annotation>();

		addAll(annotations, Arrays.asList(method.getDeclaredAnnotations()),false);

		Class<?> declaringClass = method.getDeclaringClass();

		Method otherDeclarations;
		while (!Object.class.equals(declaringClass)) {
			otherDeclarations = getFromClass(declaringClass, method);
			if (otherDeclarations != null) {
				addAll(annotations, Arrays.asList(otherDeclarations.getDeclaredAnnotations()),true);
			}
			for (Class<?> implInterface : declaringClass.getInterfaces()) {
				otherDeclarations = getFromClass(implInterface, method);
				if (otherDeclarations != null) {
					addAll(annotations, Arrays.asList(otherDeclarations.getDeclaredAnnotations()),true);
				}
			}
			declaringClass = declaringClass.getSuperclass();
		}

		return Collections.unmodifiableSet(annotations);
	}

	private void addAll(Set<Annotation> annotations, Collection<Annotation> annotationsToBeAdded, boolean inheritedOnly) {
		for (Annotation annotationToBeAdded : annotationsToBeAdded) {
			if (inheritedOnly) {
				if (annotationToBeAdded.annotationType().isAnnotationPresent(MethodInherited.class)) {
					annotations.add(annotationToBeAdded);
				}
			} else {
				annotations.addAll(annotationsToBeAdded);
			}
		}
	}
}
