package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * registers the UrlSourceJob at the SchedulingService
 */
@Component
public class UrlSourceJobRegistrar {

    private SchedulingService schedulingService;

    @Autowired
    public UrlSourceJobRegistrar(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
        this.schedulingService.registerJob(UrlSourceJob.JOBTYPE, UrlSourceJob.class);
    }

    @PostConstruct
    public void register() {
        schedulingService.registerJob(UrlSourceJob.JOBTYPE, UrlSourceJob.class);
    }

}
