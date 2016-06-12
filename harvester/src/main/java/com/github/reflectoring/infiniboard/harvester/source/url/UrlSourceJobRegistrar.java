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

    @Autowired
    private SchedulingService schedulingService;

    @PostConstruct
    public void register() {
        schedulingService.registerJob("UrlSource", UrlSourceJob.class);
    }

}
