package com.github.osvaldopina.composedannotation.properties;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfiguration;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfigurations;
import com.github.osvaldopina.composedannotation.test.annotation.*;
import org.junit.Before;
import org.junit.Test;

public class AnnotationPropertiesCreatorTest {

	private ComposedAnnotationConfigurations configurations;

	private AnnotationPropertiesCreator annotationPropertiesCreator;


	@Before
	public void setUp() {

		configurations = new ComposedAnnotationConfigurations(Arrays.asList(
				new ComposedAnnotationConfiguration(BaseAnnotation.class,BaseAnnotationBuilder.class)));

		annotationPropertiesCreator = new AnnotationPropertiesCreator();
	}

	@Test
	public void readComposedAnnotatedClass() throws Exception {
		BaseAnnotationProperties baseAnnotationProperties = annotationPropertiesCreator.create(configurations,
				 BaseAnnotation.class,	BaseAnnotationProperties.class,
				 ComposedAnnotatedClass.class.getAnnotation(ComposedAnnotation.class));

		assertThat(baseAnnotationProperties.getProp1(), is("VALUE2"));
		assertThat(baseAnnotationProperties.getProp2(), is(typeCompatibleWith(Integer.class)));


	}

	@Test
	public void readBaseAnnotatedClass() throws Exception {
		BaseAnnotationProperties baseAnnotationProperties = annotationPropertiesCreator.create(configurations,
				BaseAnnotation.class,	BaseAnnotationProperties.class,
				BaseAnnotatedClass.class.getAnnotation(BaseAnnotation.class));

		assertThat(baseAnnotationProperties.getProp1(), is("value-for-prop1"));
		assertThat(baseAnnotationProperties.getProp2(), is(typeCompatibleWith(String.class)));

	}


	@ComposedAnnotation(prop1 = AnyEnum.VALUE2, prop2 = Integer.class)
	public static class ComposedAnnotatedClass {
	}

	@BaseAnnotation(prop1 = "value-for-prop1" , prop2 = String.class)
	public static class BaseAnnotatedClass {
	}

}