package com.github.osvaldopina.composedannotation;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.properties.AnnotationPropertiesCreator;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfigurations;
import com.github.osvaldopina.composedannotation.discover.ComposedAnnotationCache;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class ComposedAnnotationPropertiesCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private AnnotationPropertiesCreator annotationPropertiesCreator;

	@Mock
	private ComposedAnnotationConfigurations configurations;

	@Mock
	private ComposedAnnotationCache composedAnnotationCache;

	private Class<? extends Annotation> baseAnnotationType = Override.class;

	private Class<?> annotationPropertiesType = Object.class;

	private Method method = Object.class.getMethods()[0];

	@Mock
	private Object annotationProperties;

	@Mock
	private AnnotatedElement annotatedElement;

	@Mock
	private Annotation composedAnnotation;

	@TestSubject
	private ComposedAnnotationReader composedAnnotationReader = new ComposedAnnotationReader(configurations);

	@Test
	public void read_annotatedElement() throws Exception {
		expect(composedAnnotationCache.getAnnotation(annotatedElement,baseAnnotationType)).andReturn(composedAnnotation);
		expect(annotationPropertiesCreator.create(configurations,baseAnnotationType, annotationPropertiesType, composedAnnotation)).
				andReturn(annotationProperties);
		replayAll();

		assertThat(composedAnnotationReader.read(annotatedElement, baseAnnotationType, annotationPropertiesType),
				is(sameInstance(annotationProperties)));

		verifyAll();
	}

	@Test
	public void read_annotatedElement_nullComposedAnnotation() throws Exception {
		expect(composedAnnotationCache.getAnnotation(annotatedElement, baseAnnotationType)).andReturn(null);
		replayAll();

		assertThat(composedAnnotationReader.read(annotatedElement, baseAnnotationType, annotationPropertiesType),
				is(nullValue()));

		verifyAll();
	}

	@Test
	public void read_methodParameter() throws Exception {
		expect(composedAnnotationCache.getAnnotation(method, 0,baseAnnotationType)).andReturn(composedAnnotation);
		expect(annotationPropertiesCreator.create(configurations,baseAnnotationType, annotationPropertiesType, composedAnnotation)).
				andReturn(annotationProperties);
		replayAll();

		assertThat(composedAnnotationReader.read(method, 0, baseAnnotationType, annotationPropertiesType),
				is(sameInstance(annotationProperties)));

		verifyAll();
	}

	@Test
	public void read_methodParameter_nullComposedAnnotation() throws Exception {
		expect(composedAnnotationCache.getAnnotation(method, 0, baseAnnotationType)).andReturn(null);
		replayAll();

		assertThat(composedAnnotationReader.read(method, 0, baseAnnotationType, annotationPropertiesType),
				is(nullValue()));

		verifyAll();
	}




}