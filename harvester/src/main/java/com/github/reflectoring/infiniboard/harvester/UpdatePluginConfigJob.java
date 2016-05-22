package com.github.reflectoring.infiniboard.harvester;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfigRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class UpdatePluginConfigJob implements Job {

    //TODO delete me

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePluginConfigJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Modified SourceConfig");
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SourceConfigRepository repository = applicationContext.getBean(SourceConfigRepository.class);

        SourceConfig tempSoureConfig = repository.findOne("sc");
        tempSoureConfig.setLastModified(new Date());
        repository.insert(tempSoureConfig);
    }

}
