package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.BridgeMethodResolver;

public class BridgedMethodFinder {

	public static final BridgedMethodFinder INSTANCE = new BridgedMethodFinder();


	public Method getCorrespondent(Method method) {

		if (method.isBridge()) {
			return getCorrespondentMethod(method);
		}
		else {
			return getBridgedMethod(method);
		}
	}

	protected  Method getCorrespondentMethod(Method bridged) {
		assert bridged != null;
		assert bridged.isBridge();

		return BridgeMethodResolver.findBridgedMethod(bridged);
	}

	protected Method getBridgedMethod(Method nonBridged) {
		assert nonBridged != null;
		assert !nonBridged.isBridge();

		for (Method candidate : nonBridged.getDeclaringClass().getDeclaredMethods()) {
			if (candidate.isBridge())
				if (BridgeMethodResolver.findBridgedMethod(candidate).equals(nonBridged)) {
					return candidate;
				}
		}
		return null;
	}
}
