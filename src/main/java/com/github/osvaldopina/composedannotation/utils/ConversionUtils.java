package com.github.osvaldopina.composedannotation.utils;

public class ConversionUtils {

	public static final ConversionUtils INSTANCE = new ConversionUtils();

	protected ConversionUtils() {
	}

	public <T> T convert(Object value, Class<T> type) {
		if (value == null) {
			return null;
		}
		if (value != null && (!(value instanceof String)) && (type.equals(String.class))) {
			return (T) value.toString();
		} else {
			return (T) value;
		}
	}
}
