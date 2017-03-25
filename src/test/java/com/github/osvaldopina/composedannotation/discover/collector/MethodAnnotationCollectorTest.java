package com.github.osvaldopina.composedannotation.discover.collector;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

public class MethodAnnotationCollectorTest {

	private MethodAnnotationCollector methodAnnotationCollector;

	@Before
	public void setUp() {
		methodAnnotationCollector = new MethodAnnotationCollector();
	}

	@Test
	public void canBeAppliedTo_simpleObject() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aClassMethod");

		assertThat(methodAnnotationCollector.canBeAppliedTo(method), is(true));
	}

	@Test
	@Ignore
	public void collectAll_simpleObject_inheritedMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(SecondAnnotation.class),
				new AnnotationTypeMatcher(FithAnnotation.class)));
	}

	@Test
	@Ignore
	public void collectAll_cglibProxy_inheritedMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("aClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(SecondAnnotation.class),
				new AnnotationTypeMatcher(FithAnnotation.class)));
	}

	@Test
	@Ignore
	public void collectAll_simpleObject_interfaceMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aInterfaceMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(FirstAnnotation.class),
				new AnnotationTypeMatcher(ForthAnnotation.class)));
	}

	@Test
	@Ignore
	public void collectAll_cglibProxy_interfaceMethodMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("aInterfaceMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(ForthAnnotation.class),
				new AnnotationTypeMatcher(FirstAnnotation.class)));
	}

	@Test
	public void collectAll_simpleObject_notInheritedMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("aSubClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(1));

		assertThat(annotations, contains(
				new AnnotationTypeMatcher(ThirdAnnotation.class)));
	}

	@Test
	@Ignore
	public void collectAll_cglibProxy_notInheritedMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("aSubClassMethod");

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(1));

		assertThat(annotations, contains(
				new AnnotationTypeMatcher(ThirdAnnotation.class)));
	}

	@Test
	@Ignore
	public void collectAll_simpleObject_bridgedMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Method method = aSubClass.getClass().getMethod("bridgedMethod", Integer.class);

		AnnotationUtils.getAnnotations(method);

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(SixthAnnotation.class),
				new AnnotationTypeMatcher(SeventhAnnotation.class)));
	}

	@Test
	@Ignore
	public void collectAll_cglibProxy_bridgedMethod() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Method method = proxied.getClass().getMethod("bridgedMethod", Integer.class);

		Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(SixthAnnotation.class),
				new AnnotationTypeMatcher(SeventhAnnotation.class)));
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface FirstAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface SecondAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ThirdAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ForthAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface FithAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface SixthAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface SeventhAnnotation {

	}

	public static interface AInterface {

		@FirstAnnotation
		void aInterfaceMethod();
	}

	public static interface BridgedMethodInterface<T> {

		@SixthAnnotation
		void bridgedMethod(T param);
	}

	public static class AClass {

		@SecondAnnotation
		public void aClassMethod() {

		}
	}

	public static class ASubClass extends AClass implements AInterface, BridgedMethodInterface<Integer> {

		@ThirdAnnotation
		public void aSubClassMethod() {

		}


		@Override
		@ForthAnnotation
		public void aInterfaceMethod() {

		}

		@Override
		@FithAnnotation
		public void aClassMethod() {
			super.aClassMethod();
		}

		@SeventhAnnotation
		public void bridgedMethod(Integer param) {

		}

	}

	public static class AnnotationTypeMatcher extends BaseMatcher<Annotation> {

		private Class<? extends Annotation> annotationType;
		private Annotation annotation;
		private boolean notAnAnnotation;

		public AnnotationTypeMatcher(Class<? extends Annotation> annotationType) {
			this.annotationType = annotationType;
		}


		@Override
		public boolean matches(Object item) {

			if (!(item instanceof Annotation)) {
				notAnAnnotation = false;
				return false;
			} else {
				notAnAnnotation = true;
				annotation = (Annotation) item;
				return annotation.annotationType().equals(annotationType);
			}
		}

		@Override
		public void describeTo(Description description) {
			if (notAnAnnotation) {
				description.appendText("is not an annotation");
			} else {
				description.appendText("expecting annotation type " + annotationType);
			}
		}
	}
}