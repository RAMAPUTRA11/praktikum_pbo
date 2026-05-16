package com.pajak.taxmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mengarahkan URL /img/ ke folder fisik di dalam project
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:src/main/resources/static/img/");
    }
}