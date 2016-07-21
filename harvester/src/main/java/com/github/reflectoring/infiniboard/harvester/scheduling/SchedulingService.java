package com.github.reflectoring.infiniboard.harvester.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * job scheduling service using quartz
 */
@Service
public class SchedulingService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulingService.class);

    public static final String PARAM_CONTEXT = "applicationContext";

    public static final String GROUP_HARVESTER = "harvester";

    private Map<String, Class<? extends SourceJob>> jobMap = new HashMap<>();

    private ApplicationContext context;

    private final Scheduler scheduler;

    private WidgetConfigRepository widgetConfigRepository;

    private SourceDataRepository sourceDataRepository;

    /**
     * initializes the scheduling service and starts an quartz scheduler
     *
     * @param context
     *         the spring context will be inserted into the job configuration so that each job can access the spring
     *         beans
     * @param widgetConfigRepository
     *         used to check for widget deletion
     * @param sourceDataRepository
     *         used to clear job data
     *
     * @throws SchedulerException
     *         if there is a problem with the underlying <code>Scheduler</code>
     */
    @Autowired
    public SchedulingService(ApplicationContext context, WidgetConfigRepository widgetConfigRepository,
                             SourceDataRepository sourceDataRepository)
            throws SchedulerException {
        this.context = context;
        this.widgetConfigRepository = widgetConfigRepository;
        this.sourceDataRepository = sourceDataRepository;

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler        scheduler        = schedulerFactory.getScheduler();
        scheduler.start();
        this.scheduler = scheduler;
    }

    /**
     * registers a class as a job under the given type name <br/>
     * Each type is unique, registering a job for an already existing type name gives an exception.
     *
     * @param type
     *         is the unique key that is used to access the class
     * @param clazz
     *         jobs of the given type are instances of this class
     *
     * @throws JobTypeAlreadyRegisteredException
     *         if the key was already used to register a job
     */
    synchronized public void registerJob(String type, Class<? extends SourceJob> clazz) {
        if (jobMap.containsKey(type)) {
            throw new JobTypeAlreadyRegisteredException(
                    String.format("job type %s is already registered by %s", type, jobMap.get(type)));
        }
        jobMap.put(type, clazz);
        LOG.debug("registered job {} as '{}'", clazz.getSimpleName(), type);
    }

    /**
     * adds application context to job data therefore allowing jobs to use spring beans
     */
    private JobDataMap createContextData() {
        JobDataMap map = new JobDataMap();
        map.put(PARAM_CONTEXT, context);
        return map;
    }

    /**
     * schedules a source update job with its configuration (containing the update time interval)
     *
     * @param group
     *         identifier of associated widget
     * @param config
     *         configuration to create job (e.g. type, name, parameters)
     *
     * @throws SchedulerException
     *         if the Job or Trigger cannot be added to the Scheduler, or there is an internal Scheduler error.
     * @throws NoSuchJobTypeException
     *         if the job type was not registered
     * @throws JobAlreadyScheduledException
     *         if the job was already scheduled (combination of group and name already used)
     */
    public void scheduleJob(String group, SourceConfig config)
            throws SchedulerException {
        String type = config.getType();
        String name = config.getId();

        if (!jobMap.containsKey(type)) {
            throw new NoSuchJobTypeException(String.format("no job of type %s was found", type));
        }

        if (checkJobExists(name, group)) {
            throw new JobAlreadyScheduledException(
                    String.format("job of type %s, group %s, and name%s already exists, could not be scheduled", type,
                                  group, name));
        }

        JobDetail job = newJob(jobMap.get(type))
                .withIdentity(config.getId(), group)
                .usingJobData(new JobDataMap(config.getConfigData()))
                .usingJobData(createContextData())
                .build();
        Trigger trigger = newTrigger()
                .withIdentity(config.getId(), group)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                      .withIntervalInMilliseconds(config.getInterval())
                                      .repeatForever())
                .build();
        scheduler.scheduleJob(job, trigger);
        LOG.debug("scheduled job ({}:{}) of type {} for {} ms", group, config.getId(), config.getType(),
                  config.getInterval());
    }

    /**
     * checks if a special job is scheduled
     *
     * @param name
     *         name of job
     * @param group
     *         name of widget
     *
     * @throws SchedulerException
     *         if there is a problem with the underlying <code>Scheduler</code>
     */
    public boolean checkJobExists(String name, String group)
            throws SchedulerException {
        return scheduler.checkExists(new JobKey(name, group));
    }

    /**
     * deletes all jobs of given group (aka widget)
     *
     * @param group
     *         identifier of widget containing the jobs to delete
     *
     * @throws SchedulerException
     *         if there is an internal Scheduler error.
     */
    public void cancelJobs(String group)
            throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(group));
        if (jobKeys.isEmpty()) {
            LOG.warn("no jobs found for group {}", group);
            return;
        }
        scheduler.deleteJobs(new ArrayList<>(jobKeys));
        LOG.debug("canceled jobs of group {}", group);
    }


    /**
     * looks if the given group (aka widget) still exists and deletes all associated data and jobs otherwise
     *
     * @param group
     *         identifier of widget the job belongs to
     *
     * @throws SchedulerException
     *         if there is an internal Scheduler error.
     */
    public boolean canSourceJobBeExecuted(String group)
            throws SchedulerException {
        if (GROUP_HARVESTER.equals(group) || widgetConfigRepository.exists(group)) {
            return true;
        }

        LOG.debug("no widget {} found - deleting data and canceling jobs");
        sourceDataRepository.deleteByWidgetId(group);
        cancelJobs(group);

        return false;
    }
}
