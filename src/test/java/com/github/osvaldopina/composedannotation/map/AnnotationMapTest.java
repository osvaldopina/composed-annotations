package com.github.osvaldopina.composedannotation.map;

import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AnnotationMapTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	MethodMap methodMap;

	Annotation annotation;

	@Mock
	ComposedAnnotationPropertiesBuilder composedAnnotationPropertiesBuilder;

	@Mock
	AnnotationMap annotationMap;


	@Before
	public void setUp() {
		annotationMap = new AnnotationMap(Arrays.asList(methodMap));
	}

	@Test
	public void getMethodMaps() throws Exception {
		assertThat(annotationMap.getMethodMaps().size(), is(1));
		assertThat(annotationMap.getMethodMaps(), contains(methodMap));
	}

	@Test
	public void transferFromAnnotationToBuilder() throws Exception {
		methodMap.transferFromAnnotationToBuilder(annotation, composedAnnotationPropertiesBuilder);
		expectLastCall();

		replayAll();

		annotationMap.transferFromAnnotationToBuilder(annotation, composedAnnotationPropertiesBuilder);

		verifyAll();

	}
}