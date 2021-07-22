package com.prime.auth.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Primary
@EnableAutoConfiguration
public class SwaggerConfig implements SwaggerResourcesProvider {

	@Bean
	public Docket configerContrrolerPackageAndConvertors() {

		Set<String> protocols = Collections.singleton("http");
		return new Docket(DocumentationType.SWAGGER_2)//
				.protocols(protocols).select()//
				.apis(RequestHandlerSelectors.basePackage("com.prime.auth.controller"))//
				.build().apiInfo(api());

	}

	ApiInfo api() {
		return new ApiInfoBuilder().title("Prime Erp").build();
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(swaggerResource("Authentication API", "/v2/api-docs", "1.0"));
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource resource = new SwaggerResource();
		resource.setName(name);
		resource.setLocation(location);
		resource.setSwaggerVersion(version);
		return resource;

	}

}
