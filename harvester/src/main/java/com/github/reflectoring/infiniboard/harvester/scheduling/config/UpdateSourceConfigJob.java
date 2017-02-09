package com.github.reflectoring.infiniboard.harvester.scheduling.config;

import com.github.reflectoring.infiniboard.harvester.scheduling.JobAlreadyScheduledException;
import com.github.reflectoring.infiniboard.harvester.scheduling.NoSuchJobTypeException;
import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/** reads the configurations of the sources and schedules the corresponding jobs */
public class UpdateSourceConfigJob implements Job {

  private static final Logger LOG = LoggerFactory.getLogger(UpdateSourceConfigJob.class);

  /** name used for registering this job */
  public static final String JOBTYPE = "updatePlugins";

  private List<WidgetConfig> getUpdatedWidgetConfigs(
      ApplicationContext context, LocalDateTime previousExecutionTime) {

    WidgetConfigRepository repository = context.getBean(WidgetConfigRepository.class);
    List<WidgetConfig> newWidgets;
    if (previousExecutionTime == null) {
      newWidgets = repository.findAll();
    } else {
      newWidgets = repository.findAllByLastModifiedAfter(previousExecutionTime);
    }
    LOG.debug("updating '{}' widgets", newWidgets.size());
    return newWidgets;
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    LocalDateTime previousExecutionTime = convertToLocalDateTime(context.getPreviousFireTime());
    LOG.debug(
        "executing update source configs at '{}' - getting sources since '{}'",
        formatIsoDateTime(context.getFireTime()),
        formatIsoDateTime(previousExecutionTime));
    JobDataMap configuration = context.getMergedJobDataMap();
    ApplicationContext applicationContext =
        (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
    SchedulingService schedulingService = applicationContext.getBean(SchedulingService.class);

    for (WidgetConfig widget : getUpdatedWidgetConfigs(applicationContext, previousExecutionTime)) {
      String widgetId = widget.getId();
      try {
        schedulingService.cancelJobs(widgetId);
      } catch (SchedulerException e) {
        LOG.error("failed to remove jobs of widget '{}'", widgetId, e);
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

  private String formatIsoDateTime(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
  }

  private String formatIsoDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return convertToLocalDateTime(date).format(DateTimeFormatter.ISO_DATE_TIME);
  }

  private LocalDateTime convertToLocalDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
