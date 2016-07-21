package com.github.reflectoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat")
public class Quartermaster {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Quartermaster.class).run(args);
    }

}
