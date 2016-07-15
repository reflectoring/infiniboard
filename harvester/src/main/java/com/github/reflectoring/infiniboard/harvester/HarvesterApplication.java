package com.github.reflectoring.infiniboard.harvester;

import javax.annotation.PostConstruct;
import java.util.Collections;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(HarvesterApplication.class);

    @Autowired
    private SchedulingService schedulingService;

    /**
     * after startup this method registers the {@link UpdatePluginConfigJob} that reads the widget configurations na
     * schedules the corresponding jobs
     *
     * @throws SchedulerException
     *         if the Job or Trigger cannot be added to the Scheduler, or there is an internal Scheduler error.
     */
    @PostConstruct
    public void startScheduling()
            throws SchedulerException {
        LOG.debug("configure update job");
        schedulingService.registerJob(UpdatePluginConfigJob.JOBTYPE, UpdatePluginConfigJob.class);
        schedulingService.scheduleJob(SchedulingService.GROUP_HARVESTER,
                                      new SourceConfig("updatePlugins", UpdatePluginConfigJob.JOBTYPE, 5000,
                                                       Collections.emptyMap()));
    }

    /**
     * used to start the spring boot application
     */
    public static void main(String[] args)
            throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }

}
