package com.github.reflectoring.infiniboard.harvester.source;

import java.util.Map;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;

/**
 * This base class of SourceJobs gets information from the quartz JobExecutionContext and calls internal method with
 * Spring application context and SourceJob configuration data.
 */
public abstract class SourceJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(SourceJob.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        LOG.debug("executing job {} ", getClass().getSimpleName());
        JobDataMap         configuration      = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SchedulingService  schedulingService  = applicationContext.getBean(SchedulingService.class);

        JobKey jobKey = context.getJobDetail().getKey();
        try {
            if (schedulingService.canSourceJobBeExecuted(jobKey.getGroup())) {
                executeInternal(applicationContext, jobKey, configuration);
            }
        } catch (SchedulerException e) {
            LOG.error("error on check if a job can be canceled", e);
            // handling?
        }
    }

    protected abstract void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration);
}
