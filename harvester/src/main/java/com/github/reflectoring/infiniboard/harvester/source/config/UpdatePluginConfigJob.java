package com.github.reflectoring.infiniboard.harvester.source.config;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.source.WidgetConfigRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * reads the configurations of the sources and schedules the corresponding jobs
 */
public class UpdatePluginConfigJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePluginConfigJob.class);

    private Date lastChecked;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        WidgetConfigRepository repository = applicationContext.getBean(WidgetConfigRepository.class);

        Date now = new Date();
        List<WidgetConfig> newWidgets = lastChecked == null ? repository.findAll() : repository.findByLastModifiedAfter(lastChecked);
        lastChecked = now;


    }
}
