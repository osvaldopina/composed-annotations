package com.github.osvaldopina.composedannotation.discover;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.finder.ComposedAnnotationFinder;
import com.github.osvaldopina.composedannotation.finder.ComposedAnnotationFinderTest;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ComposedAnnotationDiscoverTest {

	private ComposedAnnotationDiscover composedAnnotationDiscover = new ComposedAnnotationDiscover();


	private Method baseAnnotationMethod = ComposedAnnotationDiscoverTest.class.
			getMethod("baseAnnotationMethod", String.class);

	private Method composedAnnotationMethod = ComposedAnnotationDiscoverTest.class.
			getMethod("composedAnnotationMethod", String.class);

	private Method otherAnnotationMethod = ComposedAnnotationDiscoverTest.class.
			getMethod("otherAnnotationMethod", String.class);



	public ComposedAnnotationDiscoverTest() throws NoSuchMethodException {
	}

	@Test
	public void getAnnotation_annotationElement_baseAnnotation() {

		Annotation annotation = composedAnnotationDiscover.getAnnotation(baseAnnotationMethod, BaseAnnotation.class);

		assertThat(annotation, is(instanceOf(BaseAnnotation.class)));

	}

	@Test
	public void getAnnotation_annotationElement_componsedAnnotation() {

		Annotation annotation = composedAnnotationDiscover.getAnnotation(composedAnnotationMethod, BaseAnnotation.class);

		assertThat(annotation, is(instanceOf(ComposedAnnotation.class)));

	}

	@Test
	public void getAnnotation_annotationElement_otherAnnotation() {

		Annotation annotation = composedAnnotationDiscover.getAnnotation(otherAnnotationMethod, BaseAnnotation.class);

		assertThat(annotation, is(nullValue()));

	}

	@Test
	public void getAnnotation_methodParameter_baseAnnotation() {

		Annotation annotation = composedAnnotationDiscover.getAnnotation(baseAnnotationMethod,0, BaseAnnotation.class);

		assertThat(annotation, is(instanceOf(BaseAnnotation.class)));

	}

	@Test
	public void getAnnotation_methodParameter_componsedAnnotation() {

		Annotation annotation = composedAnnotationDiscover.getAnnotation(composedAnnotationMethod,0, BaseAnnotation.class);

		assertThat(annotation, is(instanceOf(ComposedAnnotation.class)));

	}

	@Test
	public void getAnnotation_methodParameter_otherAnnotation() {

		Annotation annotation = composedAnnotationDiscover.getAnnotation(otherAnnotationMethod,0, BaseAnnotation.class);

		assertThat(annotation, is(nullValue()));

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
	public static @interface BaseAnnotation {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.PARAMETER})
	@BaseAnnotation
	public static @interface ComposedAnnotation {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.PARAMETER})
	public static @interface OtherAnnotation {
	}

	@BaseAnnotation
	public void baseAnnotationMethod(@BaseAnnotation String parameter) {

	}

	@ComposedAnnotation
	public void composedAnnotationMethod(@ComposedAnnotation String parameter) {

	}

	@OtherAnnotation
	public void otherAnnotationMethod(@OtherAnnotation String parameter) {

	}



}