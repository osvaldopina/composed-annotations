package com.github.osvaldopina.composedannotation.properties;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfiguration;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfigurations;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;
import com.github.osvaldopina.composedannotation.map.AnnotationMap;
import com.github.osvaldopina.composedannotation.map.AnnotationMapDiscover;
import com.github.osvaldopina.composedannotation.utils.ReflectionUtils;

public class AnnotationPropertiesCreator {

	public static final AnnotationPropertiesCreator INSTANCE = new AnnotationPropertiesCreator();


	private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;

	private AnnotationMapDiscover annotationMapDiscover = AnnotationMapDiscover.INSTANCE;

	protected AnnotationPropertiesCreator() {
	}

	public <T> T create(ComposedAnnotationConfigurations configurations,
						Class<? extends Annotation> baseAnnotationType,
						Class<T> annotationPropertiesType, Annotation composedAnnotation) {
		assert configurations != null : "Configurations must not be null";
		assert baseAnnotationType != null : "baseAnnotationType must not be null";
		assert annotationPropertiesType != null : "annotationPropertiesType must not be null";
		assert composedAnnotation != null : "composedAnnotation must not be null";

		ComposedAnnotationConfiguration configuration = configurations.getConfiguration(baseAnnotationType);

		AnnotationMap annotationMap = annotationMapDiscover.
				getAnnotationMap(composedAnnotation.annotationType(), configuration);

		ComposedAnnotationPropertiesBuilder builder = reflectionUtils.createInstance(configuration.getBuilderType());

		annotationMap.transferFromAnnotationToBuilder(composedAnnotation, builder);

		Object annotationProperties = builder.build();
		return annotationPropertiesType.cast(annotationProperties);
	}


}
