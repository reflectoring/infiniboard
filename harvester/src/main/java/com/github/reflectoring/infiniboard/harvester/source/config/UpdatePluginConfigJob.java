package com.github.reflectoring.infiniboard.harvester.source.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.JobAlreadyScheduledException;
import com.github.reflectoring.infiniboard.harvester.scheduling.NoSuchJobTypeException;
import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

/**
 * reads the configurations of the sources and schedules the corresponding jobs
 */
public class UpdatePluginConfigJob extends SourceJob {

    private static final Logger LOG = LoggerFactory.getLogger(UpdatePluginConfigJob.class);

    /**
     * name used for registering this job
     */
    public static final String JOBTYPE = "updatePlugins";

    // job is instantiated at each call
    private static LocalDateTime lastChecked;

    @Override
    protected void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration) {
        WidgetConfigRepository repository        = context.getBean(WidgetConfigRepository.class);
        SchedulingService      schedulingService = context.getBean(SchedulingService.class);

        LocalDateTime now = LocalDateTime.now();
        List<WidgetConfig> newWidgets =
                (lastChecked == null) ? repository.findAll() : repository.findAllByLastModifiedAfter(lastChecked);
        lastChecked = now;

        LOG.debug("updating {} widgets at {}", newWidgets.size(), now.format(DateTimeFormatter.ISO_DATE_TIME));

        for (WidgetConfig widget : newWidgets) {
            String widgetId = widget.getId();
            try {
                schedulingService.cancelJobs(widgetId);
            } catch (SchedulerException e) {
                LOG.error("failed to remove jobs of widget " + widgetId, e);
                // handling?
            }
            for (SourceConfig source : widget.getSourceConfigs()) {
                try {
                    schedulingService.scheduleJob(widgetId, source);
                } catch (SchedulerException | JobAlreadyScheduledException | NoSuchJobTypeException e) {
                    LOG.error("failed to schedule job", e);
                    // handling?
                }
            }
        }
    }
}
