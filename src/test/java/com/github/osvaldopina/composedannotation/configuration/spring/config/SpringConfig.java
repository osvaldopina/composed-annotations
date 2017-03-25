package com.github.osvaldopina.composedannotation.configuration.spring.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	public static BeanPostProcessor firstAopPostProcessor() {
		return new FirstAopPostProcessor();
	}

	@Bean
	public static BeanPostProcessor secondAopPostProcessor() {
		return new SecondAopPostProcessor();
	}

	@Bean
	public AnyBean anyBean() {
		return new AnyBean();
	}

}
