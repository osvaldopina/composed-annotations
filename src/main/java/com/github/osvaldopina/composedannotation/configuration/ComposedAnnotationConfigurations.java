package com.github.osvaldopina.composedannotation.configuration;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComposedAnnotationConfigurations {

	private Map<Class<? extends Annotation>, ComposedAnnotationConfiguration> configurations =
			new HashMap<Class<? extends Annotation>, ComposedAnnotationConfiguration>();


	public ComposedAnnotationConfigurations(List<ComposedAnnotationConfiguration> configurations) {
		assert configurations != null : "configurations must not be null";
		assert configurations.size() > 0: "configurations must not be empty";

		for (ComposedAnnotationConfiguration configuration : configurations) {
			assert !this.configurations.containsKey(configuration.getBaseAnnotationType()) :
					"More than one ComposedAnnotationConfiguration registered for annotation type " +
							configuration.getBaseAnnotationType();

			this.configurations.put(configuration.getBaseAnnotationType(), configuration);
		}
	}

	public ComposedAnnotationConfiguration getConfiguration(Class<? extends Annotation> baseAnnotationType) {
		assert baseAnnotationType != null : "baseAnnotationType must not be null";

		ComposedAnnotationConfiguration configuration = configurations.get(baseAnnotationType);

		if (configuration == null) {
			throw new RuntimeException("Could not find composed annotation configuration for " + baseAnnotationType);
		}
		return configuration;
	}
}
