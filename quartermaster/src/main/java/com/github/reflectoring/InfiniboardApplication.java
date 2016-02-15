package com.github.reflectoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class InfiniboardApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(InfiniboardApplication.class).run(args);
    }
}
