package com.github.reflectoring.infiniboard.harvester.source.widget;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.config.UpdatePluginConfigJob;
import com.github.reflectoring.infiniboard.harvester.source.url.UrlSourceRetrieveJob;
import com.github.reflectoring.infiniboard.packrat.source.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.*;

/**
 * job to retrieve UrlSource (configured via DB)
 */
public class ConfigJobManagerJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(ConfigJobManagerJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        SourceConfigRepository sourceConfigRepository = applicationContext.getBean(SourceConfigRepository.class);
        UrlSourceRepository urlSourceRepository = applicationContext.getBean(UrlSourceRepository.class);
        SchedulingService schedulingService = applicationContext.getBean(SchedulingService.class);


        //1.
        //Search for deleted SourceConfigs and unschedule the corresponding jobs.
        //Delete the Sourceconfig and Configdatas of DB afterwards
        List<SourceConfig> deletedSourceconfigs = sourceConfigRepository.findByIsDeleted(true);
        for(SourceConfig tempSourceConfig : deletedSourceconfigs){
            for(ConfigSource configData : tempSourceConfig.getConfigData().values()) {
                try {
                    String name = configData.getName();
                    String group = tempSourceConfig.getWidgetId();

                    if (schedulingService.jobIsScheduled(name, group)) {
                        schedulingService.unscheduleJob(name, group);
                    }

                    urlSourceRepository.deleteById(configData.getId());
                } catch (SchedulerException e) {
                    //TODO LOG
                }
            }

            sourceConfigRepository.deleteById(tempSourceConfig.getId());
        }


        //2.
        //Search for modified Sourceconfigs and apply changes to the corresponding update-configdata-jobs
        Date lastExecutionDate = new Date();
        List<SourceConfig> modifiedSourceConfigs = sourceConfigRepository.findByLastModifiedAfter(lastExecutionDate);

        for(SourceConfig tempSourceConfig : modifiedSourceConfigs){
            for(ConfigSource configData : tempSourceConfig.getConfigData().values()) {
                try {
                    String name = configData.getName();
                    String group = tempSourceConfig.getWidgetId();

                    int updateInterval = configData.getUpdateInterval();

                    if (schedulingService.jobIsScheduled(name, group)) {
                        schedulingService.rescheduleJob(name, group, updateInterval);
                    }else{
                        schedulingService.scheduleJob(name, group, UrlSourceRetrieveJob.class, updateInterval);
                    }
                } catch (SchedulerException e) {
                    //TODO LOG
                }
            }
        }
    }
}
