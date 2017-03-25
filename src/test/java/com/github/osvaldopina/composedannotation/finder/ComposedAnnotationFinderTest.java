package com.github.osvaldopina.composedannotation.finder;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.lang.annotation.*;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class ComposedAnnotationFinderTest {

	private ComposedAnnotationFinder composedAnnotationFinder;

	private Method baseAnnotationMethod = ComposedAnnotationFinderTest.class.getMethod("baseAnnotationMethod");

	private Method composedAnnotationMethod = ComposedAnnotationFinderTest.class.getMethod("composedAnnotationMethod");

	private Method otherAnnotationMethod = ComposedAnnotationFinderTest.class.getMethod("otherAnnotationMethod");

	public ComposedAnnotationFinderTest() throws NoSuchMethodException {
	}

	@Before
	public void setUp() {
		composedAnnotationFinder = new ComposedAnnotationFinder();
	}

	@Test
	public void getAnnotation_baseAnnotation() {

		Annotation annotation = composedAnnotationFinder.getAnnotation(
				baseAnnotationMethod.getDeclaredAnnotations(),ComposedAnnotationFinderTest.BaseAnnotation.class);

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation,  instanceOf(ComposedAnnotationFinderTest.BaseAnnotation.class));

	}

	@Test
	public void getAnnotation_composedAnnotation() {

		Annotation annotation = composedAnnotationFinder.getAnnotation(
				composedAnnotationMethod.getDeclaredAnnotations(), ComposedAnnotationFinderTest.BaseAnnotation.class);

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation,  instanceOf(ComposedAnnotationFinderTest.ComposedAnnotation.class));

	}

	@Test
	public void getAnnotation_otherAnnotation() {

		Annotation annotation = composedAnnotationFinder.getAnnotation(
				otherAnnotationMethod.getDeclaredAnnotations(), ComposedAnnotationFinderTest.BaseAnnotation.class);

		assertThat(annotation, is(nullValue()));

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
	public static @interface BaseAnnotation {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@BaseAnnotation
	public static @interface ComposedAnnotation {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface OtherAnnotation {
	}

	@BaseAnnotation
	public void baseAnnotationMethod() {

	}

	@ComposedAnnotation
	public void composedAnnotationMethod() {

	}

	@OtherAnnotation
	public void otherAnnotationMethod() {

	}


}