package com.mfy.memefy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * The {@link WebConfig} class
 *
 * @author Oleh Ivasiuk
 */
@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://192.168.0.120:3000", "http://localhost:4173", "http://192.168.0.120:4173", "https://memefy.up.railway.app"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Access-Control-Allow-Origin", "X-Requested-With", "Accept"));
        corsConfiguration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
