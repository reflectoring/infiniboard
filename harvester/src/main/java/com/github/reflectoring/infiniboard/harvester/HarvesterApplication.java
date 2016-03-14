package com.github.reflectoring.infiniboard.harvester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * application class for harvester service
 */
@SpringBootApplication
@EnableScheduling
public class HarvesterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }

}