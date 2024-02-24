package com.codecrafter.typhoon.config.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final MemberResolver memberResolver;

	@Value("${file.base.dir}")
	private String fileStorePath;

	public WebMvcConfig(MemberResolver memberResolver) {
		this.memberResolver = memberResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(memberResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String home = System.getProperty("user.home");
		registry.addResourceHandler("/api/file/static/**")
			.addResourceLocations("file:" + home + "/fileStore/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE");
	}

}
