package com.github.osvaldopina.composedannotation;

public class ComposedAnnotationError extends RuntimeException {

	public ComposedAnnotationError(String message, Throwable cause) {
		super(message, cause);
	}

	public ComposedAnnotationError(String message) {
		super(message);
	}
}