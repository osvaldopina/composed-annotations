package com.github.osvaldopina.composedannotation.map;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationConfiguration;
import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

public class AnnotationMapDiscoverTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	AnnotationMapFactory annotationMapFactory;

	@Mock
	AnnotationMap annotationMap;

	@Mock
	ComposedAnnotationConfiguration configuration;

	@TestSubject
	AnnotationMapDiscover annotationMapDiscover = new AnnotationMapDiscover();

	@Test
	public void getAnnotationMap() {
		EasyMock.<Class<? extends Annotation>>expect(configuration.getBaseAnnotationType()).
				andReturn(Override.class);
		EasyMock.<Class<? extends ComposedAnnotationPropertiesBuilder>>expect(configuration.getBuilderType()).
				andReturn(ComposedAnnotationPropertiesBuilder.class);
		expect(annotationMapFactory.create(Override.class,ComposedAnnotationPropertiesBuilder.class)).andReturn(annotationMap);

		replayAll();

		assertThat(annotationMapDiscover.getAnnotationMap(Override.class, configuration), is(annotationMap));

		verifyAll();
	}

	@Test
	public void getAnnotationMap_checkCache() {
		// first call - use annotationMapFactory
		EasyMock.<Class<? extends Annotation>>expect(configuration.getBaseAnnotationType()).
				andReturn(Override.class);
		EasyMock.<Class<? extends ComposedAnnotationPropertiesBuilder>>expect(configuration.getBuilderType()).
				andReturn(ComposedAnnotationPropertiesBuilder.class);
		expect(annotationMapFactory.create(Override.class,ComposedAnnotationPropertiesBuilder.class)).andReturn(annotationMap);

		// second call - must not use annotationMapFactory
		EasyMock.<Class<? extends Annotation>>expect(configuration.getBaseAnnotationType()).
				andReturn(Override.class);

		replayAll();

		assertThat(annotationMapDiscover.getAnnotationMap(Override.class, configuration), is(annotationMap));
		assertThat(annotationMapDiscover.getAnnotationMap(Override.class, configuration), is(annotationMap));

		verifyAll();
	}

}