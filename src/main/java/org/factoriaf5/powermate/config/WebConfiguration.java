package org.factoriaf5.powermate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("http://localhost:8080")
            .allowedMethods("GET","POST","PUT","PATCH","DELETE")
            .allowCredentials(true)
            .allowedHeaders("*");
    }
}
