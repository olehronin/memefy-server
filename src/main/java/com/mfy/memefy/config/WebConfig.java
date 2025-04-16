package com.mfy.memefy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The {@link WebConfig} class
 *
 * @author Oleh Ivasiuk
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://app.up.railway.app")
                .allowedMethods("GET", "PUT", "PATCH", "POST", "DELETE");
    }
}
