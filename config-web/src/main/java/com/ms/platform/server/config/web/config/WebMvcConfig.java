package com.ms.platform.server.config.web.config;

import com.ms.common.web.interceptor.AnnotationAuthorityInterceptor;
import com.ms.platform.server.config.web.interceptor.MonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

//	@Bean
//	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}

	@Bean
	public MonitorInterceptor monitorInterceptor(){
		return new MonitorInterceptor();
	}

	@Bean
	public AnnotationAuthorityInterceptor annotationAuthorityInterceptor(){
		return new AnnotationAuthorityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(requestLimitInterceptor()).addPathPatterns("/**");//
		registry.addInterceptor(annotationAuthorityInterceptor()).addPathPatterns("/**");//userid
		registry.addInterceptor(monitorInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}

}
