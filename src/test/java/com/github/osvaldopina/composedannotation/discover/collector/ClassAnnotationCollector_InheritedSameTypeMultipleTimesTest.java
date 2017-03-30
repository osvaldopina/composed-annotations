package com.github.osvaldopina.composedannotation.discover.collector;


import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Before;
import org.junit.Test;

public class ClassAnnotationCollector_InheritedSameTypeMultipleTimesTest {

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

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				annotation(InterfaceAnnotation.class, "ASubClass"),
				annotation(ClassAnnotation.class , "ASubClass")));
	}

	@Test
	public void collectAll_cglibProxy_subClass() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(2));

		assertThat(annotations, containsInAnyOrder(
				annotation(InterfaceAnnotation.class, "ASubClass"),
				annotation(ClassAnnotation.class , "ASubClass")));
	}

	@Test
	public void collectAll_jdkProxy() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithJdk(aSubClass, ASubSubInterface.class);

		Class<?> clazz = proxied.getClass();

		Set<Annotation> annotations = classAnnotationCollector.collectAll(clazz);

		assertThat(annotations, hasSize(1));


		assertThat(annotations, contains(
				annotation(InterfaceAnnotation.class,"ASubSubInterface")));
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface InterfaceAnnotation {
		String value();
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public static @interface ClassAnnotation {
		String value();
	}

	@InterfaceAnnotation("AInterface")
	public static interface AInterface {

	}

	@InterfaceAnnotation("ASubInterface")
	public static interface ASubInterface extends AInterface {

	}

	@InterfaceAnnotation("ASubSubInterface")
	public static interface ASubSubInterface extends ASubInterface {

	}

	@ClassAnnotation("AClass")
	public static class AClass implements ASubInterface {

	}

	@ClassAnnotation("ASubClass")
	@InterfaceAnnotation("ASubClass")
	public static class ASubClass extends AClass implements ASubSubInterface {

	}
}