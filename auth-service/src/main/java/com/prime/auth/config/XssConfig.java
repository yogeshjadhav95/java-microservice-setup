package com.prime.auth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prime.auth.filter.XSSFilter;

@Configuration
public class XssConfig {

	@Bean
	public FilterRegistrationBean<XSSFilter> xssPreventFilter() {
		FilterRegistrationBean<XSSFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new XSSFilter());
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}
}
