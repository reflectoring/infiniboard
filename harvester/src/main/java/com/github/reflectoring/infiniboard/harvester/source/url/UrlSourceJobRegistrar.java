package com.github.reflectoring.infiniboard.harvester.source.url;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;

/**
 * registers the UrlSourceJob at the SchedulingService
 */
@Component
public class UrlSourceJobRegistrar {

    private SchedulingService schedulingService;

    @Autowired
    public UrlSourceJobRegistrar(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostConstruct
    public void register() {
        schedulingService.registerJob("UrlSource", UrlSourceJob.class);
    }

}
