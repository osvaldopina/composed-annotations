package com.github.osvaldopina.composedannotation.configuration.spring;

import java.lang.reflect.Method;

import com.github.osvaldopina.composedannotation.configuration.spring.config.AnyBean;
import com.github.osvaldopina.composedannotation.configuration.spring.config.FirstAopPostProcessor;
import com.github.osvaldopina.composedannotation.configuration.spring.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAopAnnotatedElementCleanerTest {


	private AnnotationConfigApplicationContext context;

	private AnyBean originalAnyBean;

	private AnyBean aopAnyBean;

	private Class<?> originalAnyBeanClass;

	private Class<?> aopAnyBeanClass;

	private Method orginalAMethod;

	private Method aopAMethod;

//	private SpringAopAnnotatedElementCleaner springAopAnnotatedElementCleaner;


	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext(SpringConfig.class);
		aopAnyBean = context.getBean(AnyBean.class);
		aopAMethod = aopAnyBean.getClass().getMethod("aMethod");
		aopAnyBeanClass = aopAnyBean.getClass();

		FirstAopPostProcessor firstAopPostProcessor = context.getBean(FirstAopPostProcessor.class);
		originalAnyBean = firstAopPostProcessor.getOriginal();
		orginalAMethod = originalAnyBean.getClass().getMethod("aMethod");
		originalAnyBeanClass = originalAnyBean.getClass();

//		springAopAnnotatedElementCleaner = new SpringAopAnnotatedElementCleaner();

	}

	@Test
	public void test() {
	//	Class<?> anyBeanClass = (Class<?>) springAopAnnotatedElementCleaner.clean(aopAnyBeanClass);


	}





}