package com.github.osvaldopina.composedannotation.discover.collector;

import javax.naming.CommunicationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.osvaldopina.composedannotation.ComposedAnnotationError;

public class AnnotationCollectors  {

	private Set<AnnotationCollector> annotationCollectors = new HashSet<AnnotationCollector>(Arrays.asList(
			MethodAnnotationCollector.INSTANCE,
			ClassAnnotationCollector.INSTANCE,
			FieldConstructorCollector.INSTANCE));

	public Set<Annotation> collectAll(AnnotatedElement annotatedElement) {
		return get(annotatedElement).collectAll(annotatedElement);
	}

	private AnnotationCollector get(AnnotatedElement annotatedElement) {
		for(AnnotationCollector annotationCollector: annotationCollectors) {
			if (annotationCollector.canBeAppliedTo(annotatedElement)) {
				return annotationCollector;
			}
		}
		throw new ComposedAnnotationError("Could not find a AnnotationCollector for type " + annotatedElement.getClass());
	}

}
