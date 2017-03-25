package com.github.osvaldopina.composedannotation.map;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.osvaldopina.composedannotation.configuration.ComposedAnnotationPropertiesBuilder;

public class AnnotationMap {

	private List<MethodMap> methodMaps = new ArrayList<>();

	protected AnnotationMap(List<MethodMap> methodMaps) {
		this.methodMaps.addAll(methodMaps);
	}

	public List<MethodMap> getMethodMaps() {
		return Collections.unmodifiableList(methodMaps);
	}

	public void transferFromAnnotationToBuilder(Annotation annotation,
												ComposedAnnotationPropertiesBuilder composedAnnotationPropertiesBuilder) {

		for(MethodMap methodMap: methodMaps) {
			methodMap.transferFromAnnotationToBuilder(annotation, composedAnnotationPropertiesBuilder);
		}

	}



}
