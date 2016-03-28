package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.overseer.source.SourceConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * job scheduling service using quartz
 */
@Service
public class SchedulingService {

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
    public void scheduleJob(String name, String group, Class<? extends Job> clazz, SourceConfig config) throws SchedulerException {
        JobDetail job = newJob(clazz)
                .withIdentity(name, group)
                .usingJobData(new JobDataMap(config.getConfigData()))
                .usingJobData(createContextData())
                .build();
        Trigger trigger = newTrigger()
                .withIdentity(name, group)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(config.getInterval())
                        .repeatForever())
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * schedules a job with time interval to be repeated
     */
    public void scheduleJob(String name, String group, Class<? extends Job> clazz, Integer interval) throws SchedulerException {
        JobDetail job = newJob(clazz)
                .withIdentity(name, group)
                .usingJobData(createContextData())
                .build();
        Trigger trigger = newTrigger()
                .withIdentity(name, group)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(interval)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * cancels a job by name and group
     */
    public void cancelJob(String name, String group) throws SchedulerException {
        JobKey key = new JobKey(name, group);
        if (scheduler.checkExists(key)) {
            scheduler.deleteJob(key);
        }
    }

}
