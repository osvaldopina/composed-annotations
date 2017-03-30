package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import com.github.osvaldopina.composedannotation.annotation.MethodInherited;

public class FieldConstructorCollector implements AnnotationCollector {

    public static final FieldConstructorCollector INSTANCE = new FieldConstructorCollector();

    private BridgedMethodFinder bridgedMethodFinder = BridgedMethodFinder.INSTANCE;

    protected FieldConstructorCollector() {

    }

    @Override
    public Set<Annotation> collectAll(AnnotatedElement annotatedElement) {
        return Collections.unmodifiableSet(
                new HashSet<Annotation>(Arrays.asList(annotatedElement.getDeclaredAnnotations())));
    }

    @Override
    public boolean canBeAppliedTo(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Field || annotatedElement instanceof Constructor;
    }

}
