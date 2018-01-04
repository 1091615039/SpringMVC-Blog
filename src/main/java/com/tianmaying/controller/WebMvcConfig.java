package com.tianmaying.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/blogs/create");
		//registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**");
	}

}
