package com.github.osvaldopina.composedannotation.discover.collector;


import static com.github.osvaldopina.composedannotation.discover.collector.AnnotationTypeMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import com.github.osvaldopina.composedannotation.annotation.MethodInherited;
import com.github.osvaldopina.composedannotation.configuration.spring.config.AopAdvice;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests Inheritance of annotations in methods.
 * <p>
 * The rule tested here is:
 * Returns all annotations in the method annotatedElement and searchs
 * for this method signature in all superclasses and interfaces. In case
 * the method is found checks if it is annotated with @MethodInterited and
 * then returns all its annotations.
 */
public class MethodAnnotationCollector_MethodInheritedTest {

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

        Method method = aSubClass.getClass().getMethod("aSubClassMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(1));

        assertThat(annotations, contains(
                annotation(ASubClass_ASubClassMethodAnnotation.class, "ASubClass")));
    }

    /**
     * checks for annotations on ASubclass.aSubClassMethod.
     * Must return @ASubClass_ASubClassMethodAnnotation("ASubClass"). Because
     * this method exists only in ASubClass. Note that this annotation is returnoed
     * because it is annotated with @MethodInherited because CGLib proxies creates a
     * subclass when enhances the object.
     */
    @Test
    public void collectAll_cglibProxy_subClassMethod() throws Exception {
        ASubClass aSubClass = new ASubClass();

        Object proxied = AopAdvice.proxyWithCglib(aSubClass);

        Method method = proxied.getClass().getMethod("aSubClassMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(1));

        assertThat(annotations, contains(
                annotation(ASubClass_ASubClassMethodAnnotation.class, "ASubClass")));
    }

    /**
     * checks for annotations on ASubclass.aSubClassMethod.
     * Must return @ASubClass_AInterfaceMethodAnnotation("ASubClass") and
     *
     * @AInterface_AInterfaceMethod("AInterface"). Because
     * this method exists in ASubClass and in AInterface and are annotated with @MethodInherited.
     */
    @Test
    public void collectAll_simpleObject_interfaceMethod() throws Exception {
        ASubClass aSubClass = new ASubClass();

        Method method = aSubClass.getClass().getMethod("aInterfaceMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(2));

        assertThat(annotations, containsInAnyOrder(
                annotation(ASubClass_AInterfaceMethodAnnotation.class, "ASubClass"),
                annotation(AInterface_AInterfaceMethod.class, "AInterface")));
    }

    /**
     * checks for annotations on ASubclass.aSubClassMethod.
     * Must return @ASubClass_AInterfaceMethodAnnotation("ASubClass") and
     *
     * @AInterface_AInterfaceMethod("AInterface"). Because
     * this method exists in ASubClass and in AInterface and are annotated with @MethodInherited.
     * Note that these annotations are returnoed
     * because they are annotated with @MethodInherited. CGLib proxies creates a
     * subclass when enhances the object.
     */
    @Test
    public void collectAll_cglibProxy_interfaceMethod() throws Exception {
        ASubClass aSubClass = new ASubClass();

        Object proxied = AopAdvice.proxyWithCglib(aSubClass);

        Method method = proxied.getClass().getMethod("aInterfaceMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(2));

        assertThat(annotations, containsInAnyOrder(
                annotation(ASubClass_AInterfaceMethodAnnotation.class, "ASubClass"),
                annotation(AInterface_AInterfaceMethod.class, "AInterface")));
    }

    /**
     * checks for annotations on ASubclass.aClassMethod.
     * Must return @ASubClass_AInterfaceMethodAnnotation("ASubClass") and
     *
     * @AInterface_AInterfaceMethod("AInterface"). Because
     * this method exists in ASubClass and in AInterface and are annotated with @MethodInherited.
     */
    @Test
    public void collectAll_simpleObject_classMethod() throws Exception {
        ASubClass aSubClass = new ASubClass();

        Method method = aSubClass.getClass().getMethod("aClassMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(2));

        assertThat(annotations, containsInAnyOrder(
                annotation(AClass_AClassMethodAnnotation.class, "AClass"),
                annotation(ASubClass_AClassMethodAnnotation.class, "ASubClass")));
    }

    /**
     * checks for annotations on ASubclass.aClassMethod.
     * Must return @ASubClass_AInterfaceMethodAnnotation("ASubClass") and
     *
     * @AInterface_AInterfaceMethod("AInterface"). Because
     * this method exists in ASubClass and in AInterface and are annotated with @MethodInherited.
     * Note that these annotations are returnoed
     * because they are annotated with @MethodInherited. CGLib proxies creates a
     * subclass when enhances the object.
     */
    @Test
    public void collectAll_cglibProxy_classMethod() throws Exception {
        ASubClass aSubClass = new ASubClass();

        Object proxied = AopAdvice.proxyWithCglib(aSubClass);

        Method method = aSubClass.getClass().getMethod("aClassMethod");

        Set<Annotation> annotations = methodAnnotationCollector.collectAll(method);

        assertThat(annotations, hasSize(2));

        assertThat(annotations, containsInAnyOrder(
                annotation(AClass_AClassMethodAnnotation.class, "AClass"),
                annotation(ASubClass_AClassMethodAnnotation.class, "ASubClass")));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @MethodInherited
    public static @interface AInterface_AInterfaceMethod {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @MethodInherited
    public static @interface AClass_AClassMethodAnnotation {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @MethodInherited
    public static @interface ASubClass_ASubClassMethodAnnotation {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @MethodInherited
    public static @interface ASubClass_AInterfaceMethodAnnotation {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @MethodInherited
    public static @interface ASubClass_AClassMethodAnnotation {

        String value();
    }

    public static interface AInterface {

        @AInterface_AInterfaceMethod("AInterface")
        void aInterfaceMethod();
    }

    public static class AClass {

        @AClass_AClassMethodAnnotation("AClass")
        public void aClassMethod() {
        }
    }

    public static class ASubClass extends AClass implements AInterface {

        @ASubClass_ASubClassMethodAnnotation("ASubClass")
        public void aSubClassMethod() {
        }

        @Override
        @ASubClass_AInterfaceMethodAnnotation("ASubClass")
        public void aInterfaceMethod() {
        }

        @Override
        @ASubClass_AClassMethodAnnotation("ASubClass")
        public void aClassMethod() {
        }
    }
}