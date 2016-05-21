package com.github.reflectoring.infiniboard.harvester;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.sourceConfig.ConfigJobManagerJob;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;

/**
 * application class for harvester service
 */
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat.source")
@SpringBootApplication
public class HarvesterApplication {

    @Autowired
    private SchedulingService schedulingService;

    @PostConstruct
    public void startScheduling() throws SchedulerException {
        schedulingService.scheduleJob("source", "harvester", ConfigJobManagerJob.class, 5);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }
}
