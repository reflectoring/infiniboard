package com.github.reflectoring.infiniboard.harvester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * application class for harvester service
 */
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat")
@SpringBootApplication
public class HarvesterApplication {

    /**
     * used to start the spring boot application
     *
     * @param args
     *         start parameters
     */
    public static void main(String[] args)
            throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }

}
