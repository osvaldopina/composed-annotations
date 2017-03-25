package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClassAnnotationCollector implements AnnotationCollector {


	@Override
	public Set<Annotation> collectAll(AnnotatedElement annotatedElement) {
		Set<Annotation> annotations = new HashSet<Annotation>();

		Class<?> clazz = (Class) annotatedElement;

		annotations.addAll(Arrays.asList(clazz.getDeclaredAnnotations()));

		for (Class<?> intf : clazz.getInterfaces()) {
			annotations.addAll(collectAll(intf));
		}
		if (clazz.getSuperclass() != null) {
			annotations.addAll(collectAll(clazz.getSuperclass()));
		}

		return Collections.unmodifiableSet(annotations);
	}

	@Override
	public boolean canBeAppliedTo(AnnotatedElement annotatedElement) {
		return annotatedElement instanceof Class;
	}

}
