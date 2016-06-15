package com.github.reflectoring.infiniboard.harvester.source;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * This base class of SourceJobs gets information from the quartz JobExecutionContext and calls internal method with Spring application context and SourceJob
 * configuration data.
 */
public abstract class SourceJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(SourceJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SchedulingService schedulingService = applicationContext.getBean(SchedulingService.class);

        JobKey jobKey = context.getJobDetail().getKey();
        try {
            if(schedulingService.canSourceJobBeExecuted(jobKey.getGroup())) {
                executeInternal(applicationContext, jobKey, configuration);
            }
        } catch (SchedulerException e) {
            LOG.error("error on check if a job can be canceled", e);
            // handling?
        }
    }

    protected abstract void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration);
}
