package com.github.osvaldopina.composedannotation.configuration.spring.config;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class AopAdvice implements AfterReturningAdvice {


	public AopAdvice() {
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
	}

	public static Object proxyWithCglib(Object object) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new AopAdvice());

		factory.setTarget(object);
		return factory.getProxy();
	}

	public static Object proxyWithJdk(Object object, Class<?>...proxyInterface) {
		ProxyFactory factory = new ProxyFactory();
		factory.setInterfaces(proxyInterface);

		factory.setTarget(object);
		return factory.getProxy();
	}
}
