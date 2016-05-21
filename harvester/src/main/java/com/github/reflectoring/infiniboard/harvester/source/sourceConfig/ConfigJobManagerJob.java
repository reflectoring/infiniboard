package com.github.reflectoring.infiniboard.harvester.source.sourceConfig;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.SourceRetrieveJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfigRepository;
import com.github.reflectoring.infiniboard.packrat.source.SourceRepository;
import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;


public class ConfigJobManagerJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(ConfigJobManagerJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SourceConfigRepository sourceConfigRepository = applicationContext.getBean(SourceConfigRepository.class);
        SourceRepository sourceRepository = applicationContext.getBean(SourceRepository.class);
        SchedulingService schedulingService = applicationContext.getBean(SchedulingService.class);


        //1.
        //Search for deleted SourceConfigs and unschedule the corresponding jobs.
        //Delete the Sourceconfig and Configdatas of DB afterwards
        List<SourceConfig> deletedSourceconfigs = sourceConfigRepository.findByIsDeleted(true);
        for(SourceConfig tempSourceConfig : deletedSourceconfigs){
            for (UrlSource tempUrlSource : tempSourceConfig.getUrlSources()) {
                try {
                    String name = String.valueOf(tempUrlSource.getId());
                    String group = tempSourceConfig.getWidgetId();

                    if (schedulingService.jobIsScheduled(name, group)) {
                        schedulingService.unscheduleJob(name, group);
                    }

                    sourceRepository.deleteById(tempUrlSource.getId());
                } catch (SchedulerException e) {
                    LOG.error(String.format("Could not delete ConfigdataId %s couse of ", tempUrlSource.getId()), e);
                }
            }

            sourceConfigRepository.deleteById(tempSourceConfig.getId());
        }


        //2.
        //Search for modified Sourceconfigs and apply changes to the corresponding update-configdata-jobs
        Date lastExecutionDate = new Date();
        List<SourceConfig> modifiedSourceConfigs = sourceConfigRepository.findByLastModifiedAfter(lastExecutionDate);
        for(SourceConfig tempSourceConfig : modifiedSourceConfigs){
            for (UrlSource tempUrlSource : tempSourceConfig.getUrlSources()) {
                try {
                    String name = String.valueOf(tempUrlSource.getId());
                    String group = tempSourceConfig.getWidgetId();
                    int updateInterval = tempUrlSource.getUpdateInterval();

                    if (schedulingService.jobIsScheduled(name, group)) {
                        schedulingService.rescheduleJob(name, group, updateInterval);
                    }else{
                        schedulingService.scheduleJob(name, group, SourceRetrieveJob.class, updateInterval);
                    }
                } catch (SchedulerException e) {
                    LOG.error(String.format("Could not schedule ConfigdataId %s couse of ", tempUrlSource.getId()), e);
                }
            }
        }
    }
}
