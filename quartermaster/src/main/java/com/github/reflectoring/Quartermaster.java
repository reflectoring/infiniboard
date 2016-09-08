package com.github.reflectoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat")
public class Quartermaster {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Quartermaster.class).run(args);
    }

    /*
    * Activates Cross-Origin Resource Sharing (CORS)
    * for local development of JavaScript client.
    *
    * https://de.wikipedia.org/wiki/Cross-Origin_Resource_Sharing
    */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**/*")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

}
