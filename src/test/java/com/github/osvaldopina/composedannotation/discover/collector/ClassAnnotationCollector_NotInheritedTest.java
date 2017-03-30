package com.github.osvaldopina.composedannotation.discover.collector;


import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.annotation;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Before;
import org.junit.Test;

public class ClassAnnotationCollector_NotInheritedTest {

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

		assertThat(annotations, hasSize(1));

		assertThat(annotations, contains(annotation(ASubClassAnnotation.class)));
	}

	@Test
	public void collectAll_cglibProxy_subClass() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(0));

	}

	@Test
	public void collectAll_jdkProxy() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithJdk(aSubClass, ASubSubInterface.class, OtherInterface.class);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				annotation(ASubSubInterfaceAnnotation.class),
				annotation(OtherInterfaceAnnotation.class)));
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface AInterfaceAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ASubInterfaceAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ASubSubInterfaceAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface AClassAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ASubClassAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface OtherInterfaceAnnotation {

	}

	@AInterfaceAnnotation
	public static interface AInterface {

	}

	@ASubInterfaceAnnotation
	public static interface ASubInterface extends AInterface {

	}

	@ASubSubInterfaceAnnotation
	public static interface ASubSubInterface extends ASubInterface {

	}

	@OtherInterfaceAnnotation
	public static interface OtherInterface {

	}

	@AClassAnnotation
	public static class AClass implements ASubInterface {

	}

	@ASubClassAnnotation
	public static class ASubClass extends AClass implements ASubSubInterface {

	}

}