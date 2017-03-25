package com.github.osvaldopina.composedannotation.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConversionUtilsTest {

	private ConversionUtils conversionUtils;

	@Before
	public void setUp() {
		conversionUtils = new ConversionUtils();
	}

	@Test
	public void convert_toString() throws Exception {
		assertThat(conversionUtils.convert(Integer.valueOf(10), String.class), is("10"));
	}

	@Test
	public void convert_sameType() throws Exception {
		assertThat(conversionUtils.convert(Integer.valueOf(10), Integer.class), is(10));
	}

	@Test
	public void convert_primitiveToWrapper() throws Exception {
		assertThat(conversionUtils.convert(Integer.valueOf(10), int.class), is(10));
	}

}