package com.github.osvaldopina.composedannotation.discover.collector;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class BridgedMethodFinderTest {

	BridgedMethodFinder bridgedMethodFinder;

	Method methodWithCorrepondentBrigde = AClass.class.getMethod("aMethod", Integer.class);

	Method methodWithoutCorrepondentBrigde = AClass.class.getMethod("aMethod", String.class);

	Method bridgedMethod = AClass.class.getMethod("aMethod", Object.class);

	public BridgedMethodFinderTest() throws NoSuchMethodException {
	}

	@Before
	public void setUp() {
		bridgedMethodFinder = new BridgedMethodFinder();
	}


	@Test
	public void getCorrespondent() {
		assertThat(bridgedMethodFinder.getCorrespondent(methodWithCorrepondentBrigde), is(bridgedMethod));
		assertThat(bridgedMethodFinder.getCorrespondent(bridgedMethod), is(methodWithCorrepondentBrigde));
		assertThat(bridgedMethodFinder.getCorrespondent(methodWithoutCorrepondentBrigde), is(nullValue()));
	}


	public static interface AGenericInterface<T> {

		void aMethod(T param);
	}

	public static class AClass implements AGenericInterface<Integer> {


		@Override
		public void aMethod(Integer param) {

		}

		public void aMethod(String param) {

		}
	}



}