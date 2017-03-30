package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class AnnotationTypeMatcher extends BaseMatcher<Annotation> {

	private Class<? extends Annotation> annotationType;
	private Annotation annotation;
	private boolean notAnAnnotation;
	private boolean notSameType;
	private String value;
	private String otherValue;



	public static AnnotationTypeMatcher annotation(Class<? extends Annotation> annotationType, String value) {
		return new AnnotationTypeMatcher(annotationType, value);
	}

	public static AnnotationTypeMatcher annotation(Class<? extends Annotation> annotationType) {
		return new AnnotationTypeMatcher(annotationType, null);
	}

	public AnnotationTypeMatcher(Class<? extends Annotation> annotationType, String value) {
		this.annotationType = annotationType;
		this.value = value;
	}

	@Override
	public boolean matches(Object item)  {

		if (!(item instanceof Annotation)) {
			notAnAnnotation = false;
			return false;
		}
		notAnAnnotation = true;
		annotation = (Annotation) item;
		if (!annotation.annotationType().equals(annotationType)) {
			return false;
		}
		try {
			if (value != null && ! value.equals(annotation.annotationType().getMethod("value").invoke(annotation))) {
				return false;
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("could not get value from annotation because " +e,e);
		}
		return true;
	}

	@Override
	public void describeTo(Description description) {
		if (notAnAnnotation) {
			description.appendText("is not an annotation");
		} else if (notSameType){
			description.appendText("expecting annotation type " + annotationType);
		}
		else {
			description.appendText("expecting annotation value to be " + value);
		}
	}
}
