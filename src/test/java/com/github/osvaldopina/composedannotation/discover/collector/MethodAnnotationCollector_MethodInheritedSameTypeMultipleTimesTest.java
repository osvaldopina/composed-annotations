package com.github.osvaldopina.composedannotation.discover.collector;


import com.github.osvaldopina.composedannotation.annotation.MethodInherited;
import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.annotation;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests Inheritance of annotations in methods.
 * <p>
 * The rule tested here is:
 * Returns all annotations in the method annotatedElement and searchs
 * for this method signature in all superclasses and interfaces. In case
 * the method is found checks if it is annotated with @MethodInterited and
 * then returns all its annotations.
 */
public class MethodAnnotationCollector_MethodInheritedSameTypeMultipleTimesTest {

    private MethodAnnotationCollector methodAnnotationCollector;

    @Before
    public void setUp() {
        methodAnnotationCollector = new MethodAnnotationCollector();
    }

    /**
     * checks for annotations on ASubclass.aSubClassMethod.
     * Must return @ASubClass_ASubClassMethodAnnotation("ASubClass"). Because
     * this method exists only in ASubClass
     */
    @Test
    public void collectAll_simpleObject_subClassMethod() throws Exception {
        ASubClass aSubClass = new ASubClass();

        Method method = aSubClass.getClass().getMethod("aMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(1));

        assertThat(annotations, contains(
                annotation(AMethodAnnotation.class, "ASubClass")));
    }



    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @MethodInherited
    public static @interface AMethodAnnotation {

        String value();
    }

    public static interface AInterface {

        @AMethodAnnotation("AInterface")
        void aMethod();
    }

    public static class AClass implements AInterface{

        @AMethodAnnotation("AClass")
        public void aMethod() {

        }
    }

    public static class ASubClass extends AClass implements AInterface {

        @AMethodAnnotation("ASubClass")
        public void aMethod() {

        }
    }
}