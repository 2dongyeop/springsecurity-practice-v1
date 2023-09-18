package io.dongvelop.springsecuritypractice.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*") // Client IP
                .allowCredentials(true);
    }
}