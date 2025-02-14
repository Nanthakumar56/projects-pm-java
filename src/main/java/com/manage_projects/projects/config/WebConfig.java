package com.manage_projects.projects.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
        	@Override
        	public void addCorsMappings(CorsRegistry registry) {
        	    registry.addMapping("/**")
        	            .allowedOriginPatterns("*") // Allows all origins in Spring 5+ (or use allowedOrigins("*") for older versions)
        	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        	            .allowedHeaders("*");  // Allows all headers
        	}

        };
    }
}

