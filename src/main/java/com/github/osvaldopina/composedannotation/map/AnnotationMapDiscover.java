package com.github.osvaldopina.composedannotation.map;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfiguration;

public class AnnotationMapDiscover {

	public static final AnnotationMapDiscover INSTANCE  = new AnnotationMapDiscover();

	private AnnotationMapFactory annotationMapFactory = AnnotationMapFactory.INSTANCE;

	private Map<String, AnnotationMap> annotationMaps = new ConcurrentHashMap<String, AnnotationMap>();

	protected AnnotationMapDiscover() {

	}

	public AnnotationMap getAnnotationMap(Class<? extends Annotation> annotationType,
										  ComposedAnnotationConfiguration configuration) {

		String key = annotationType.getName() + ":" + configuration.getBaseAnnotationType();

		AnnotationMap annotationMap = annotationMaps.get(key);

		if (annotationMap == null) {
			annotationMap = annotationMapFactory.create(annotationType, configuration.getBuilderType());
			annotationMaps.put(key, annotationMap);
		}

		return annotationMap;
	}

}
