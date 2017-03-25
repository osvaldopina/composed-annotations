package com.github.osvaldopina.composedannotation.configuration.spring.config;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class FirstAopPostProcessor implements BeanPostProcessor {


	private AnyBean original;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		if (AnyBean.class.isAssignableFrom(bean.getClass())) {
			original = (AnyBean) bean;
			return addInterceptorToMethods(bean);
		}
		else {
			return bean;
		}
	}

	public AnyBean getOriginal() {
		return original;
	}

	private Object addInterceptorToMethods(Object object) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new AopAdvice());

		factory.setTarget(object);
		return factory.getProxy();
	}



}
