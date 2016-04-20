package com.github.reflectoring.infiniboard.harvester.source.config;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfigRepository;

/**
 * reads the configurations of the sources and schedules the corresponding jobs
 */
public class UpdatePluginConfigJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePluginConfigJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("executing update");
        // SourceConfig config = new SourceConfig("widget", "urlsource", new Date(), 5, new HashMap<>());
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SourceConfigRepository repository = applicationContext.getBean(SourceConfigRepository.class);
        // repository.save(config);
    }
}
