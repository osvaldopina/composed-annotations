package com.github.osvaldopina.composedannotation.map;

import static org.easymock.EasyMock.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;
import com.github.osvaldopina.composedannotation.utils.ConversionUtils;
import com.github.osvaldopina.composedannotation.utils.ReflectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class MethodMapTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ReflectionUtils reflectionUtils;

	@Mock
	private ConversionUtils conversionUtils;

	@Mock
	private Annotation annotation;

	@Mock
	private ComposedAnnotationPropertiesBuilder composedAnnotationPropertiesBuilder;

	private Method builderMethod = TestClass.class.getMethod("builderMethod", int.class);

	private Method annotationMethod = TestClass.class.getMethod("annotationMethod");

	private Integer annotationValue = Integer.valueOf(100);

	private Integer convertedValue = Integer.valueOf(100);

	@TestSubject
	private MethodMap methodMap = new MethodMap(annotationMethod, builderMethod);

	public MethodMapTest() throws NoSuchMethodException {
	}


	@Test
	public void transferFromAnnotationToBuilder() throws Exception {

		expect(reflectionUtils.invoke(annotation, annotationMethod)).andReturn(annotationValue);
		expect(conversionUtils.convert(annotationValue, builderMethod.getParameterTypes()[0])).andReturn(convertedValue);
		expect(reflectionUtils.invoke(composedAnnotationPropertiesBuilder, builderMethod, convertedValue)).andReturn(null);

		replayAll();

		methodMap.transferFromAnnotationToBuilder(annotation, composedAnnotationPropertiesBuilder);

		verifyAll();

	}

	public static class TestClass {

		public void builderMethod(int value) {

		}

		public int annotationMethod() {
			return 10;
		}

	}
}