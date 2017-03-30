package com.github.osvaldopina.composedannotation.discover.collector;


import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.annotation;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Before;
import org.junit.Test;

public class MethodAnnotationCollector_NotInheritedTest {

	private MethodAnnotationCollector methodAnnotationCollector;

	@Before
	public void setUp() {
		methodAnnotationCollector = new MethodAnnotationCollector();
	}

	@Test
	public void collectAll_simpleObject_subClassMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(1));

		assertThat(annotations, contains(annotation(ASubClass_aClassMethod.class,"ASubClass")));

	}

	@Test
	public void collectAll_cglibProxy_subClassMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("aClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(0));

	}

	@Test
	public void collectAll_simpleObject_interfaceMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aInterfaceMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(1));

		assertThat(annotations, contains(annotation(ASubClass_aInterfaceMethod.class,"ASubClass")));

	}

	@Test
	public void collectAll_cglibProxy_interfaceMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("aInterfaceMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(0));

	}

	@Test
	public void collectAll_simpleObject_aSubClassMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aSubClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(1));

		assertThat(annotations, contains(annotation(ASubClass_aSubClassMethod.class,"ASubClass")));
	}

	@Test
	public void collectAll_cglibProxy_aSubClassMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("aSubClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(0));

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface AInterface_aInterfaceMethod {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface AClass_aClassMethod {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ASubClass_aSubClassMethod {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ASubClass_aInterfaceMethod {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ASubClass_aClassMethod {
		String value();
	}

	public static interface AInterface {

		@AInterface_aInterfaceMethod("AInterface")
		void aInterfaceMethod();
	}

	public static class AClass {

		@AClass_aClassMethod("AClass")
		public void aClassMethod() {
		}
	}

	public static class ASubClass extends AClass implements AInterface {

		@ASubClass_aSubClassMethod("ASubClass")
		public void aSubClassMethod() {
		}

		@Override
		@ASubClass_aInterfaceMethod("ASubClass")
		public void aInterfaceMethod() {
		}

		@Override
		@ASubClass_aClassMethod("ASubClass")
		public void aClassMethod() {
		}

	}

}