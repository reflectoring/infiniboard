package com.github.reflectoring.infiniboard.harvester.source.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * reads the configurations of the sources and schedules the corresponding jobs
 */
public class UpdatePluginConfigJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("executing update");
    }
}
