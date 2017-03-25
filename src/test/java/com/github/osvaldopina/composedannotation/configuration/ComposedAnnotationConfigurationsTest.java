package com.github.osvaldopina.composedannotation.configuration;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import com.github.osvaldopina.composedannotation.ComposedAnnotationError;
import com.github.osvaldopina.composedannotation.map.AnnotationMapFactoryTest;
import org.junit.Test;

public class ComposedAnnotationConfigurationsTest {

	private ComposedAnnotationConfigurations composedAnnotationConfigurations;

	private Class<? extends  Annotation> annotationType = Override.class;

	private Class<? extends ComposedAnnotationPropertiesBuilder> composedAnnotationPropertiesBuilder =
			ComposedAnnotationPropertiesBuilder.class;


	@Test
	public void getConfiguration() {
		composedAnnotationConfigurations = new ComposedAnnotationConfigurations(
				Arrays.asList(
				new ComposedAnnotationConfiguration(annotationType, ComposedAnnotationPropertiesBuilder.class)));

		ComposedAnnotationConfiguration configuration = composedAnnotationConfigurations.getConfiguration(Override.class);
		assertThat(configuration, is(notNullValue()));
		assertThat((Class<Override>)configuration.getBaseAnnotationType(), equalTo(Override.class));
		assertThat((Class<ComposedAnnotationPropertiesBuilder>)configuration.getBuilderType(),
				equalTo(ComposedAnnotationPropertiesBuilder.class));

	}


	@Test(expected = AssertionError.class)
	public void constructor_twoAnnotationsRegistered() {
		composedAnnotationConfigurations = new ComposedAnnotationConfigurations(
				Arrays.asList(
						new ComposedAnnotationConfiguration(annotationType, ComposedAnnotationPropertiesBuilder.class),
						new ComposedAnnotationConfiguration(annotationType, ComposedAnnotationPropertiesBuilder.class)));

	}
}