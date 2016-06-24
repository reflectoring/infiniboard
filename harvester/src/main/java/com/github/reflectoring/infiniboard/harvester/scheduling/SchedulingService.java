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

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * job scheduling service using quartz
 */
@Service
public class SchedulingService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulingService.class);

    public final static String PARAM_CONTEXT = "applicationContext";

    private Map<String, Class<? extends SourceJob>> jobMap = new HashMap<>();

    private ApplicationContext context;

    private final Scheduler scheduler;

    @Autowired
    public SchedulingService(ApplicationContext context)
            throws SchedulerException {
        this.context = context;
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler        scheduler        = schedulerFactory.getScheduler();
        scheduler.start();
        this.scheduler = scheduler;
    }

    synchronized public void registerJob(String type, Class<? extends SourceJob> clazz) {
        if (jobMap.containsKey(type)) {
            throw new RuntimeException(
                    String.format("job type %s is already registered by %s", type, jobMap.get(type)));
        }
        jobMap.put(type, clazz);
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
     */
    public void scheduleJob(String group, SourceConfig config)
            throws SchedulerException {
        String type = config.getType();
        if (!jobMap.containsKey(type)) {
            LOG.error("no job of type {} was found", type);
        } else {
            JobDetail job = newJob(jobMap.get(type)).withIdentity(config.getId(), group)
                    .usingJobData(new JobDataMap(config.getConfigData()))
                    .usingJobData(createContextData()).build();
            Trigger trigger = newTrigger().withIdentity(config.getId(), group).startNow()
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(config.getInterval())
                                    .repeatForever()).build();
            scheduler.scheduleJob(job, trigger);
        }
    }

    /**
     * checks if an job exists
     */
    public boolean checkExists(String name, String group)
            throws SchedulerException {
        return scheduler.checkExists(new JobKey(name, group));
    }

    /**
     * deletes all jobs of given group
     */
    public void cancelJobs(String group)
            throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(group));
        if (jobKeys.isEmpty()) {
            LOG.warn("no jobs found for group {}", group);
            return;
        }
        scheduler.deleteJobs(new ArrayList<>(jobKeys));
    }

}
