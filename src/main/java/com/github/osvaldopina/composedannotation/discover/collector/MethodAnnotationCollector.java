package com.github.osvaldopina.composedannotation.discover.collector;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

import com.github.osvaldopina.composedannotation.annotation.MethodInherited;

public class MethodAnnotationCollector implements AnnotationCollector {

    private BridgedMethodFinder bridgedMethodFinder = BridgedMethodFinder.INSTANCE;

    @Override
    public Set<Annotation> collectAll(AnnotatedElement annotatedElement) {
        Map<Class<? extends Annotation>,Annotation> annotations = new HashMap<Class<? extends Annotation>,Annotation>();

        Method method = (Method) annotatedElement;

        addAll(annotations, collectAllFromMethod(method), false);


        Method correspondent = bridgedMethodFinder.getCorrespondent(method);

        if (correspondent != null) {
            addAll(annotations, collectAllFromMethod(correspondent), false);
        }

        return new HashSet<Annotation>(annotations.values());
    }

    @Override
    public boolean canBeAppliedTo(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Method;
    }

    private Method getFromClass(Class<?> clazz, Method original) {
        try {
            return clazz.getMethod(original.getName(), original.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private Set<Annotation> collectAllFromMethod(Method method) {
        Map<Class<? extends Annotation>,Annotation> annotations = new HashMap<Class<? extends Annotation>,Annotation>();

        addAll(annotations, Arrays.asList(method.getDeclaredAnnotations()), false);

        Class<?> declaringClass = method.getDeclaringClass();

        Method otherDeclarations;
        while (!Object.class.equals(declaringClass)) {
            otherDeclarations = getFromClass(declaringClass, method);
            if (otherDeclarations != null) {
                addAll(annotations, Arrays.asList(otherDeclarations.getDeclaredAnnotations()), true);
            }
            for (Class<?> implInterface : declaringClass.getInterfaces()) {
                otherDeclarations = getFromClass(implInterface, method);
                if (otherDeclarations != null) {
                    addAll(annotations, Arrays.asList(otherDeclarations.getDeclaredAnnotations()), true);
                }
            }
            declaringClass = declaringClass.getSuperclass();
        }

        return new HashSet<Annotation>(annotations.values());
    }

    private void addAll(Map<Class<? extends Annotation>,Annotation> annotations, Collection<Annotation> annotationsToBeAdded, boolean inheritedOnly) {
        for (Annotation annotationToBeAdded : annotationsToBeAdded) {
            if (inheritedOnly) {
                if (isInheritedMethodAnnotated(annotationToBeAdded)) {
                    if (! annotations.containsKey(annotationToBeAdded.annotationType())) {
                        add(annotations, annotationToBeAdded);
                    }
                }
            } else {
                add(annotations, annotationToBeAdded);
            }
        }
    }

    private void add(Map<Class<? extends Annotation>,Annotation> annotations, Annotation annotationsToBeAdded) {
        if (! annotations.containsKey(annotationsToBeAdded.annotationType())) {
            annotations.put(annotationsToBeAdded.annotationType(), annotationsToBeAdded);
        }
    }

    private boolean isInheritedMethodAnnotated(Annotation annotationToBeAdded) {
        if (annotationToBeAdded.annotationType().getName().startsWith("java.lang")) {
            return false;
        }
        if (annotationToBeAdded.annotationType().isAnnotationPresent(MethodInherited.class)) {
            return true;
        }
        else  if (annotationToBeAdded.annotationType().getAnnotations().length ==0) {
            return false;
        }
        else {
            for (Annotation annotation : annotationToBeAdded.annotationType().getAnnotations()) {
                if (isInheritedMethodAnnotated(annotation)) {
                    return true;
                }
            }
            return false;
        }
    }
}
