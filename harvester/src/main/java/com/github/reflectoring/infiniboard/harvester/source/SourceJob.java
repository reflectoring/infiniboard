package com.github.reflectoring.infiniboard.harvester.source;

import java.util.Map;

import org.quartz.*;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;

/**
 * This base class of SourceJobs gets information from the quartz JobExecutionContext and calls internal method with Spring application context and SourceJob
 * configuration data.
 */
public abstract class SourceJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        executeInternal(applicationContext, context.getJobDetail().getKey(), configuration);
    }

    protected abstract void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration);

}
