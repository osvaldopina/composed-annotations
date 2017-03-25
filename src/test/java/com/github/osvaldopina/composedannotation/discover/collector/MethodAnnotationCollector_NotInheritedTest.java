package com.github.osvaldopina.composedannotation.discover.collector;


import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.annotation;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

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

		assertThat(annotations, contains(annotation(FithAnnotation.class,"ASubClass")));

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

		assertThat(annotations, contains(annotation(ForthAnnotation.class,"ASubClass")));

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

		assertThat(annotations, contains(annotation(ThirdAnnotation.class,"ASubClass")));
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
	public static @interface FirstAnnotation {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface SecondAnnotation {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ThirdAnnotation {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ForthAnnotation {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface FithAnnotation {
		String value();
	}

	public static interface AInterface {

		@FirstAnnotation("AInterface")
		void aInterfaceMethod();
	}

	public static class AClass {

		@SecondAnnotation("AClass")
		public void aClassMethod() {
		}
	}

	public static class ASubClass extends AClass implements AInterface {

		@ThirdAnnotation("ASubClass")
		public void aSubClassMethod() {
		}

		@Override
		@ForthAnnotation("ASubClass")
		public void aInterfaceMethod() {
		}

		@Override
		@FithAnnotation("ASubClass")
		public void aClassMethod() {
		}

	}

}