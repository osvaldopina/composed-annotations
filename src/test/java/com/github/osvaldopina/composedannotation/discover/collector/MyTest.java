package com.github.osvaldopina.composedannotation.discover.collector;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.util.Set;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Test;

public class MyTest {


	@Test
	public void test() throws Exception {
		ASubClass aSubClass = new ASubClass();

		Object proxied = AopAdvice.proxyWithCglib(aSubClass);

		Class<?> clazz = proxied.getClass();

		System.out.println("a" + clazz);

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.TYPE})
	@Inherited
	public static @interface FirstAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.TYPE})
	@Inherited
	public static @interface SecondAnnotation {

	}

	@SecondAnnotation
	public static interface AInterface {

		@SecondAnnotation
	    void aInterfaceMethod();
	}

	@FirstAnnotation
	public static class AClass {

		@FirstAnnotation
		public void aMethod() {
		}

	}

	public static class ASubClass extends AClass implements AInterface {

		public void aMethod() {
		}

		@Override
		public void aInterfaceMethod() {

		}
	}
}
