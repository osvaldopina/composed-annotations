package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Set;

public interface AnnotationCollector {

	Set<Annotation> collectAll(AnnotatedElement annotatedElement);

	boolean canBeAppliedTo(AnnotatedElement annotatedElement);



}
