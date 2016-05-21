package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * job scheduling service using quartz
 */
@Service
public class SchedulingService {
    private final static Logger LOG = LoggerFactory.getLogger(SchedulingService.class);

    public final static String PARAM_CONTEXT = "applicationContext";

    @Autowired
    private ApplicationContext context;

    private final Scheduler scheduler;

    public SchedulingService() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        this.scheduler = scheduler;
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
    public void scheduleJob(String name, String group, Class<? extends Job> clazz, UrlSource urlSource) throws SchedulerException {

        //Store id of UrlSource to JobDataMap
        Map<String, Integer> urlSourceInformation = new HashMap<>();
        urlSourceInformation.put("id", urlSource.getId());

        JobDetail job = newJob(clazz).withIdentity(name, group).usingJobData(new JobDataMap(urlSourceInformation)).usingJobData(createContextData()).build();
        Trigger trigger = newTrigger().withIdentity(name, group).startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(urlSource.getUpdateInterval()).repeatForever()).build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * schedules a job with time interval to be repeated
     */
    public void scheduleJob(String name, String group, Class<? extends Job> clazz, int interval) throws SchedulerException {
        JobDetail job = newJob(clazz).withIdentity(name, group).usingJobData(createContextData()).build();
        Trigger trigger = newTrigger().withIdentity(name, group).startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Update interval of already scheduled job
     */
    public void rescheduleJob(String name, String group, int newInterval) throws SchedulerException {
        Trigger oldTrigger = scheduler.getTrigger(new TriggerKey(name, group));
        TriggerBuilder triggerBuilder = oldTrigger.getTriggerBuilder();
        Trigger newTrigger = triggerBuilder.withIdentity(name, group).startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(newInterval).repeatForever()).build();
        scheduler.scheduleJob(getJobDetail(name, group), newTrigger);
    }

    /**
     * cancels a job by name and group
     */
    public void cancelJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    public JobDetail getJobDetail(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        return scheduler.getJobDetail(jobKey);
    }

    public TriggerKey getTriggerKey(String name, String group) throws SchedulerException {
        return scheduler.getTrigger(new TriggerKey(name, group)).getKey();
    }

    public boolean jobIsScheduled(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        return scheduler.checkExists(jobKey);
    }

    public void unscheduleJob(String name, String group) throws SchedulerException {
        scheduler.unscheduleJob(getTriggerKey(name, group));
        LOG.info("Unscheduled Job name: {} group {} ", name, group);
    }
}
