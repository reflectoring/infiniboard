package com.github.reflectoring.infiniboard.harvester.scheduling;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.github.reflectoring.infiniboard.harvester.scheduling.config.UpdateSourceConfigJob;
import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/** job scheduling service using quartz */
@Service
public class SchedulingService {

  private static final Logger LOG = LoggerFactory.getLogger(SchedulingService.class);

  public static final String PARAM_CONTEXT = "applicationContext";

  public static final String GROUP_HARVESTER = "harvester";

  private Map<String, Class<? extends SourceJob>> jobMap = new HashMap<>();

  private ApplicationContext context;

  private Scheduler scheduler;

  private WidgetConfigRepository widgetConfigRepository;

  private SourceDataRepository sourceDataRepository;

  /**
   * initializes the scheduling service and starts an quartz scheduler
   *
   * @param context the spring context will be inserted into the job configuration so that each job
   *     can access the spring beans
   * @param widgetConfigRepository used to check for widget deletion
   * @param sourceDataRepository used to clear job data
   */
  @Autowired
  public SchedulingService(
      ApplicationContext context,
      WidgetConfigRepository widgetConfigRepository,
      SourceDataRepository sourceDataRepository) {
    this.context = context;
    this.widgetConfigRepository = widgetConfigRepository;
    this.sourceDataRepository = sourceDataRepository;
  }

  @PostConstruct
  void setupScheduling() throws SchedulerException {
    LOG.debug("setting up scheduler");
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    Scheduler newScheduler = schedulerFactory.getScheduler();
    newScheduler.start();
    scheduler = newScheduler;

    createJobSchedule(
        GROUP_HARVESTER,
        UpdateSourceConfigJob.JOBTYPE,
        UpdateSourceConfigJob.class,
        Collections.EMPTY_MAP,
        5000);
  }

  @PreDestroy
  void cleanupScheduling() throws SchedulerException {
    LOG.debug("shutting down scheduler");
    scheduler.clear();
    scheduler.shutdown();
  }

  /**
   * registers a class as a job under the given type name <br>
   * Each type is unique, registering a job for an already existing type name gives an exception.
   *
   * @param type is the unique key that is used to access the class
   * @param clazz jobs of the given type are instances of this class
   * @throws JobTypeAlreadyRegisteredException if the key was already used to register a job
   */
  public synchronized void registerJob(String type, Class<? extends SourceJob> clazz) {
    if (jobMap.containsKey(type)) {
      throw new JobTypeAlreadyRegisteredException(
          String.format("job type '%s' is already registered by '%s'", type, jobMap.get(type)));
    }
    jobMap.put(type, clazz);
    LOG.debug("registered job '{}' as '{}'", clazz.getSimpleName(), type);
  }

  /** adds application context to job data therefore allowing jobs to use spring beans */
  private JobDataMap createContextData() {
    JobDataMap map = new JobDataMap();
    map.put(PARAM_CONTEXT, context);
    return map;
  }

  /**
   * schedules a source update job with its configuration (containing the update time interval)
   *
   * @param group identifier of associated widget
   * @param config configuration to create job (e.g. type, name, parameters)
   * @throws SchedulerException if the Job or Trigger cannot be added to the Scheduler, or there is
   *     an internal Scheduler error.
   * @throws NoSuchJobTypeException if the job type was not registered
   * @throws JobAlreadyScheduledException if the job was already scheduled (combination of group and
   *     name already used)
   */
  public void scheduleJob(String group, SourceConfig config) throws SchedulerException {
    String type = config.getType();
    String name = config.getId();
    int intervalInMillis = config.getInterval();

    if (!jobMap.containsKey(type)) {
      throw new NoSuchJobTypeException(String.format("no job of type '%s' was found", type));
    }

    if (checkJobExists(name, group)) {
      throw new JobAlreadyScheduledException(
          String.format(
              "job of type '%s', group '%s', and name '%s' already exists, could not be scheduled",
              type, group, name));
    }

    createJobSchedule(group, name, jobMap.get(type), config.getConfigData(), intervalInMillis);
  }

  private void createJobSchedule(
      String group, String name, Class<? extends Job> jobClass, Map jobConfig, int intervalInMillis)
      throws SchedulerException {
    JobDetail job =
        newJob(jobClass)
            .withIdentity(name, group)
            .usingJobData(new JobDataMap(jobConfig))
            .usingJobData(createContextData())
            .build();
    Trigger trigger =
        newTrigger()
            .withIdentity(name, group)
            .startNow()
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(intervalInMillis)
                    .repeatForever())
            .build();
    scheduler.scheduleJob(job, trigger);
    LOG.debug(
        "scheduled job ({}:{}) of class '{}' for {} ms", group, name, jobClass, intervalInMillis);
  }

  /**
   * checks if a special job is scheduled
   *
   * @param name name of job
   * @param group name of widget
   * @return true, if the scheduler knows a job with this name and widget identifier
   * @throws SchedulerException if there is a problem with the underlying <code>Scheduler</code>
   */
  public boolean checkJobExists(String name, String group) throws SchedulerException {
    return scheduler.checkExists(new JobKey(name, group));
  }

  /**
   * deletes all jobs of given group (aka widget)
   *
   * @param group identifier of widget containing the jobs to delete
   * @throws SchedulerException if there is an internal Scheduler error.
   */
  public void cancelJobs(String group) throws SchedulerException {
    Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(group));
    if (jobKeys.isEmpty()) {
      LOG.warn("no jobs found for group '{}'", group);
      return;
    }
    scheduler.deleteJobs(new ArrayList<>(jobKeys));
    LOG.debug("canceled jobs of group '{}'", group);
  }

  /**
   * looks if the given group (aka widget) still exists and deletes all associated data and jobs
   * otherwise
   *
   * @param group identifier of widget the job belongs to
   * @return true, if the widget still exists
   * @throws SchedulerException if there is an internal Scheduler error.
   */
  public boolean canSourceJobBeExecuted(String group) throws SchedulerException {
    if (GROUP_HARVESTER.equals(group) || widgetConfigRepository.exists(group)) {
      return true;
    }

    LOG.debug("no widget '{}' found - deleting data and canceling jobs");
    sourceDataRepository.deleteByWidgetId(group);
    cancelJobs(group);

    return false;
  }
}
