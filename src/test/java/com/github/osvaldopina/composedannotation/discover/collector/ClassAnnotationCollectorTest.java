package com.github.osvaldopina.composedannotation.discover.collector;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

public class ClassAnnotationCollectorTest {

	private ClassAnnotationCollector classAnnotationCollector;

	@Before
	public void setUp() {
		classAnnotationCollector = new ClassAnnotationCollector();
	}

	@Test
	public void canBeAppliedTo() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Class<?> clazz = aSubClass.getClass();

		assertThat(classAnnotationCollector.canBeAppliedTo(clazz), is(true));
	}

	@Test
	public void collectAll_subClass() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Class<?> clazz = aSubClass.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(5));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(FirstAnnotation.class),
				new AnnotationTypeMatcher(SecondAnnotation.class),
				new AnnotationTypeMatcher(ThirdAnnotation.class),
				new AnnotationTypeMatcher(ForthAnnotation.class),
				new AnnotationTypeMatcher(FifthAnnotation.class)
		));
	}

	@Test
	public void collectAll_cglibProxy_subClass() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(5));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(FirstAnnotation.class),
				new AnnotationTypeMatcher(SecondAnnotation.class),
				new AnnotationTypeMatcher(ThirdAnnotation.class),
				new AnnotationTypeMatcher(ForthAnnotation.class),
				new AnnotationTypeMatcher(FifthAnnotation.class)
		));
	}

	@Test
	public void collectAll_jdkProxy() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithJdk(aSubClass, ASubSubInterface.class, OtherInterface.class);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(4));

		assertThat(annotations, containsInAnyOrder(
				new AnnotationTypeMatcher(FirstAnnotation.class),
				new AnnotationTypeMatcher(SecondAnnotation.class),
				new AnnotationTypeMatcher(ThirdAnnotation.class),
				new AnnotationTypeMatcher(SixthAnnotation.class)
		));
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface FirstAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface SecondAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ThirdAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ForthAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface FifthAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface SixthAnnotation {

	}

	@FirstAnnotation
	public static interface AInterface {

	}

	@SecondAnnotation
	public static interface ASubInterface extends AInterface {

	}

	@ThirdAnnotation
	public static interface ASubSubInterface extends ASubInterface {

	}

	@SixthAnnotation
	public static interface OtherInterface {

	}

	@ForthAnnotation
	public static class AClass implements ASubInterface {

	}

	@FifthAnnotation
	public static class ASubClass extends AClass implements ASubSubInterface {

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