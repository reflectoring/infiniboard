package com.github.reflectoring.infiniboard.harvester;

import javax.annotation.PostConstruct;
import java.util.Collections;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.config.UpdatePluginConfigJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;

/**
 * application class for harvester service
 */
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat")
@SpringBootApplication
public class HarvesterApplication {

    @Autowired
    private SchedulingService schedulingService;

    @PostConstruct
    public void startScheduling()
            throws SchedulerException {
        schedulingService.registerJob(UpdatePluginConfigJob.JOBTYPE, UpdatePluginConfigJob.class);
        schedulingService.scheduleJob("harvester",
                                      new SourceConfig("updatePlugins", UpdatePluginConfigJob.JOBTYPE, 5000,
                                                       Collections.emptyMap()));
    }

    public static void main(String[] args)
            throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }

}
