package com.github.reflectoring.infiniboard.harvester.source.sourceConfig;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.Source.SourceRetrieveJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfigRepository;
import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import com.github.reflectoring.infiniboard.packrat.source.UrlSourceRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigJobManagerJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(ConfigJobManagerJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SourceConfigRepository sourceConfigRepository = applicationContext.getBean(SourceConfigRepository.class);
        UrlSourceRepository sourceRepository = applicationContext.getBean(UrlSourceRepository.class);
        SchedulingService schedulingService = applicationContext.getBean(SchedulingService.class);

        //1.
        //Search for deleted SourceConfigs and unschedule the corresponding jobs.
        //Delete the Sourceconfig and Configdatas of DB afterwards
        List<SourceConfig> deletedSourceconfigs = sourceConfigRepository.findByDeleted(true);
        for(SourceConfig tempSourceConfig : deletedSourceconfigs){
            for (UrlSource tempUrlSource : tempSourceConfig.getUrlSources()) {
                try {
                    String name = String.valueOf(tempUrlSource.getId());
                    String group = tempSourceConfig.getWidgetId();

                    if (schedulingService.jobIsScheduled(name, group)) {
                        schedulingService.unscheduleJob(name, group);
                    }

                    sourceRepository.delete(tempUrlSource.getId());
                } catch (SchedulerException e) {
                    LOG.error(String.format("Could not delete ConfigdataId %s couse of ", tempUrlSource.getId()), e);
                }
            }

            sourceConfigRepository.delete(tempSourceConfig.getId());
        }

        //2.
        //Search for modified Sourceconfigs and apply changes to the corresponding update-configdata-jobs
        List<SourceConfig> modifiedSourceConfigs = sourceConfigRepository.findByModified(true);
        for(SourceConfig tempSourceConfig : modifiedSourceConfigs){
            for (UrlSource tempUrlSource : tempSourceConfig.getUrlSources()) {
                try {
                    String name = String.valueOf(tempUrlSource.getId());
                    String group = tempSourceConfig.getWidgetId();
                    int updateInterval = tempUrlSource.getUpdateInterval();

                    schedulingService.cancelJob(name, group);

                    //Store id of UrlSource to JobDataMap
                    Map<String, String> urlSourceInformation = new HashMap<>();
                    urlSourceInformation.put("id", tempUrlSource.getId());
                    //TODO:  (@Stefan K.) Should i use a DTO to send Job-Data?
                    schedulingService.scheduleJob(name, group, SourceRetrieveJob.class, urlSourceInformation, updateInterval);

                } catch (SchedulerException e) {
                    LOG.error(String.format("Could not schedule ConfigdataId %s couse of ", tempUrlSource.getId()), e);
                }
            }

            tempSourceConfig.setModified(false);
            sourceConfigRepository.save(tempSourceConfig);
        }


        LOG.info(String.format("Executed ConfigJobManagerJob: Deleted SourceConfigs %s, Modified SourceConfigs %s ", deletedSourceconfigs.size(), modifiedSourceConfigs.size()));
    }
}
