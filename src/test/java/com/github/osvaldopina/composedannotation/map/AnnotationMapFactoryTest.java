package com.github.osvaldopina.composedannotation.map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.composedannotation.ComposedAnnotationError;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;
import org.junit.Before;
import org.junit.Test;

public class AnnotationMapFactoryTest {

	AnnotationMapFactory annotationMapFactory;

	Method annotationMethod = Annotation.class.getMethod("property1");
	Method builderMethod = Builder.class.getMethod("property1", String.class);

	public AnnotationMapFactoryTest() throws NoSuchMethodException {
	}


	@Before
	public void setUp() {
		annotationMapFactory = new AnnotationMapFactory();
	}

	@Test
	public void create() throws Exception {
		AnnotationMap annotationMap = annotationMapFactory.create(Annotation.class, Builder.class);

		List<MethodMap> methodMaps = annotationMap.getMethodMaps();

		assertThat(methodMaps.size(), is(1));
		assertThat(methodMaps.get(0).getAnnotationMethod(), is(annotationMethod));
		assertThat(methodMaps.get(0).getBuilderMethod(), is(builderMethod));

	}

	@Test(expected = ComposedAnnotationError.class)
	public void create_methodNotFoundBuilder() throws Exception {

		annotationMapFactory.create(AnnotationOtherProperty.class, Builder.class);

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Annotation {

		String property1();

	}

	@Annotation(
			property1 = "property1-value"
	)
	public static class AnnotatedClass {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface AnnotationOtherProperty {

		String otherProperty();

	}

	@AnnotationOtherProperty(
			otherProperty = "property1-value"
	)
	public static class AnnotatedOtherPropertyClass {

	}


	public static class Builder implements ComposedAnnotationPropertiesBuilder {

		public void property1(String value) {

		}

		@Override
		public Object build() {
			return null;
		}
	}
}