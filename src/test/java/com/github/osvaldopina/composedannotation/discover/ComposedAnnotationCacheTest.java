package com.github.osvaldopina.composedannotation.discover;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ComposedAnnotationCacheTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ComposedAnnotationDiscover composedAnnotationDiscover;

	private ComposedAnnotationCache composedAnnotationCache;

	@Mock
	private AnnotatedElement annotatedElement;

	@Mock
	private Annotation annotation;

	private Method method = Object.class.getMethod("equals", Object.class);

	public ComposedAnnotationCacheTest() throws NoSuchMethodException {
	}

	@Before
	public void setUp() {
		composedAnnotationCache = new ComposedAnnotationCache(composedAnnotationDiscover);
	}

	@Test
	public void getAnnotation_annotationElement() {
		expect(composedAnnotationDiscover.getAnnotation(annotatedElement, Override.class)).andReturn(annotation);

		replayAll();

		// first time - call composedAnnotationDiscover
		assertThat(composedAnnotationCache.getAnnotation(annotatedElement, Override.class), is(annotation));

		// second time - call gets from cache
		assertThat(composedAnnotationCache.getAnnotation(annotatedElement, Override.class), is(annotation));

		verifyAll();
	}

	@Test
	public void getAnnotation_annotationElement_discoverReturnsNull() {
		expect(composedAnnotationDiscover.getAnnotation(annotatedElement, Override.class)).andReturn(null);

		replayAll();

		// first time - call composedAnnotationDiscover
		assertThat(composedAnnotationCache.getAnnotation(annotatedElement, Override.class), is(nullValue()));

		// second time - call gets from cache
		assertThat(composedAnnotationCache.getAnnotation(annotatedElement, Override.class), is(nullValue()));

		verifyAll();
	}

	@Test
	public void getAnnotation_parameter() {
		expect(composedAnnotationDiscover.getAnnotation(method, 0 , Override.class)).andReturn(annotation);

		replayAll();

		// first time - call composedAnnotationDiscover
		assertThat(composedAnnotationCache.getAnnotation(method, 0, Override.class), is(annotation));

		// second time - call gets from cache
		assertThat(composedAnnotationCache.getAnnotation(method, 0, Override.class), is(annotation));

		verifyAll();
	}

	@Test
	public void getAnnotation_parameter_discoverReturnsNull() {
		expect(composedAnnotationDiscover.getAnnotation(method, 0, Override.class)).andReturn(null);

		replayAll();

		// first time - call composedAnnotationDiscover
		assertThat(composedAnnotationCache.getAnnotation(method, 0, Override.class), is(nullValue()));

		// second time - call gets from cache
		assertThat(composedAnnotationCache.getAnnotation(method, 0, Override.class), is(nullValue()));

		verifyAll();
	}
}