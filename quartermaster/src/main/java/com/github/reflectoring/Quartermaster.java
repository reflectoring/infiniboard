package com.github.reflectoring;

import javax.servlet.DispatcherType;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat")
public class Quartermaster {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Quartermaster.class).run(args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new UrlRewriteFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.addInitParameter("confPath", "urlrewrite.xml");
//        registrationBean.addInitParameter("statusEnabled", "true");
//        registrationBean.addInitParameter("logLevel", "DEBUG");

        return registrationBean;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/*")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }
}
