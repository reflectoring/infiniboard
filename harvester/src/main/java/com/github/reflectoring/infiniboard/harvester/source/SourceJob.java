package com.github.reflectoring.infiniboard.harvester.source;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * base class of SourceJobs
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
