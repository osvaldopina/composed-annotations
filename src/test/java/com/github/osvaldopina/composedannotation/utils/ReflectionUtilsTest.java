package com.github.osvaldopina.composedannotation.utils;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class ReflectionUtilsTest {

	private ReflectionUtils reflectionUtils;

	private Method returnsAlways10 = AnyClass.class.getMethod("returnsAlways10");

	private Method multiply = AnyClass.class.getMethod("multiply", int.class, int.class);

	private AnyClass target = new AnyClass();

	public ReflectionUtilsTest() throws NoSuchMethodException {
	}

	@Before
	public void setUp() {
		reflectionUtils = new ReflectionUtils();
	}

	@Test
	public void invoke_noParameters() throws Exception {
       assertThat((Integer) reflectionUtils.invoke(target, returnsAlways10), is(10));
	}

	@Test
	public void invoke_twoParameters() throws Exception {
		assertThat((Integer) reflectionUtils.invoke(target, multiply, 3,6), is(18));
	}

	@Test
	public void createInstance() throws Exception {
		Object instance = reflectionUtils.createInstance(AnyClass.class);

		assertThat(instance, is(notNullValue()));
		assertThat(instance.getClass(), is(typeCompatibleWith(AnyClass.class)));

	}

	@Test
	public void isObjectMethod_allObjectMethos() throws Exception {

		for(Method method: Object.class.getMethods()) {
			assertThat(reflectionUtils.isObjectMethod(method.getName(), method.getParameterTypes()), is(true));
		}

	}

	@Test
	public void isObjectMethod_nonObjectMethods() throws Exception {

		for(Method method: this.getClass().getDeclaredMethods()) {
			assertThat(reflectionUtils.isObjectMethod(method.getName(), method.getParameterTypes()), is(false));
		}

	}


	@Test
	public void getMethod_noParameters() throws Exception {
		assertThat(reflectionUtils.getMethod(AnyClass.class, "returnsAlways10"), is(returnsAlways10));
	}

	@Test
	public void getMethod_twoParameters() throws Exception {
		assertThat(reflectionUtils.getMethod(AnyClass.class, "multiply", int.class, int.class), is(multiply));
	}

	@Test
	public void getMethod_wrongParameterList() throws Exception {
		assertThat(reflectionUtils.getMethod(AnyClass.class, "multiply"), is(nullValue()));
	}

	public static class AnyClass {

		public int returnsAlways10() {
			return 10;
		}

		public int multiply(int first, int second) {
			return first * second;
		}
	}
}