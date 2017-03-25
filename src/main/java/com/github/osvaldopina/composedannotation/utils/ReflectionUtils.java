package com.github.osvaldopina.composedannotation.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.ComposedAnnotationError;

public class ReflectionUtils {

	public static final ReflectionUtils INSTANCE = new ReflectionUtils();

	protected ReflectionUtils() {
	}

	public Object invoke(Object target, Method method, Object ... parameters) {
		try {
			return method.invoke(target, parameters);
		} catch (IllegalAccessException e) {
			throw new ComposedAnnotationError("Error calling method " + method + ". " +e,e);
		} catch (InvocationTargetException e) {
			throw new ComposedAnnotationError("Error calling method " + method + ". " +e,e);
		}
	}

	public <T> T createInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (IllegalAccessException e) {
			throw new ComposedAnnotationError("Could not create instance of "  + type + " because " +e,e);
		} catch (InstantiationException e) {
			throw new ComposedAnnotationError("Could not create instance of "  + type + " because " +e,e);
		}
	}

	public boolean isObjectMethod(String methodName, Class<?>... parameterTypes) {
		try {
			Object.class.getMethod(methodName, parameterTypes);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	public Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		try {
			return clazz.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

}
