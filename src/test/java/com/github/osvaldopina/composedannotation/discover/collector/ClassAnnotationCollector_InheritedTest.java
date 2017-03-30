package com.github.osvaldopina.composedannotation.discover.collector;


import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Before;
import org.junit.Test;

public class ClassAnnotationCollector_InheritedTest {

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
				annotation(AInterfaceAnnotation.class),
				annotation(ASubInterfaceAnnotation.class),
				annotation(ASubSubInterfaceAnnotation.class),
				annotation(AClassAnnotation.class),
				annotation(ASubClassAnnotation.class)));
	}

	@Test
	public void collectAll_cglibProxy_subClass() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, containsInAnyOrder(
				annotation(AInterfaceAnnotation.class),
				annotation(ASubInterfaceAnnotation.class),
				annotation(ASubSubInterfaceAnnotation.class),
				annotation(AClassAnnotation.class),
				annotation(ASubClassAnnotation.class)));
	}

	@Test
	public void collectAll_jdkProxy() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithJdk(aSubClass, ASubSubInterface.class, OtherInterface.class);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(4));


		assertThat(annotations, containsInAnyOrder(
				annotation(OtherInterfaceAnnotation.class),
				annotation(ASubInterfaceAnnotation.class),
				annotation(ASubSubInterfaceAnnotation.class),
				annotation(AInterfaceAnnotation.class)));
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface AInterfaceAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface ASubInterfaceAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface ASubSubInterfaceAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface AClassAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface ASubClassAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
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