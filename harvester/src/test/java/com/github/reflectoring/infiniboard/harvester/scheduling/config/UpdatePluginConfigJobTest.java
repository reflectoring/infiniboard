package com.github.reflectoring.infiniboard.harvester.scheduling.config;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UpdatePluginConfigJobTest {

    private ApplicationContext applicationContext;

    private SchedulingService schedulingService;

    private WidgetConfigRepository repository;

    private JobExecutionContext jobExecutionContext;

    private final Date fireTime = new Date();

    @Before
    public void initializeMocks() {
        jobExecutionContext = mock(JobExecutionContext.class);
        when(jobExecutionContext.getFireTime()).thenReturn(fireTime);

        applicationContext = mock(ApplicationContext.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(
                new JobDataMap(Collections.singletonMap(SchedulingService.PARAM_CONTEXT, applicationContext)));

        schedulingService = mock(SchedulingService.class);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);

        repository = mock(WidgetConfigRepository.class);
        when(applicationContext.getBean(WidgetConfigRepository.class)).thenReturn(repository);
    }

    @Test
    public void firstExecutionRetrievesAll()
            throws JobExecutionException {

        UpdateSourceConfigJob job = new UpdateSourceConfigJob();
        job.execute(jobExecutionContext);

        verify(repository).findAll();
    }

    @Test
    public void laterExecutionsRetrieveUpdatedWidgetsOnly()
            throws JobExecutionException {

        Date previousFireTime = DateUtils.addMinutes(fireTime, -5);
        when(jobExecutionContext.getPreviousFireTime()).thenReturn(previousFireTime);

        UpdateSourceConfigJob job = new UpdateSourceConfigJob();
        job.execute(jobExecutionContext);

        verify(repository).findAllByLastModifiedAfter(any(LocalDateTime.class));
    }


    @Test
    public void executeInternalSchedulesAllGivenJobs()
            throws SchedulerException, NoSuchFieldException, IllegalAccessException {

        ArrayList<WidgetConfig> widgetConfigs = new ArrayList<>();
        when(repository.findAll()).thenReturn(widgetConfigs);

        widgetConfigs.add(createWidgetConfig("firstWidget", "one", "two", "three"));
        widgetConfigs.add(createWidgetConfig("secondWidget", "alpha", "beta", "gamma"));

        UpdateSourceConfigJob job = new UpdateSourceConfigJob();
        job.execute(jobExecutionContext);

        for (WidgetConfig widget : widgetConfigs) {
            // cleanup old jobs
            verify(schedulingService).cancelJobs(widget.getId());
            for (SourceConfig source : widget.getSourceConfigs()) {
                // schedule new jobs
                verify(schedulingService).scheduleJob(widget.getId(), source);
            }
        }

    }

    private WidgetConfig createWidgetConfig(String widgetLabel, String... sourceLabels)
            throws IllegalAccessException, NoSuchFieldException {
        WidgetConfig widgetConfig = new WidgetConfig(widgetLabel);

        // making id accessible
        Field idField = WidgetConfig.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(widgetConfig, widgetLabel);

        for (String label : sourceLabels) {
            widgetConfig.add(new SourceConfig(label, "TestUrlSourceJob", 500, Collections.emptyMap()));
        }
        return widgetConfig;
    }

}
