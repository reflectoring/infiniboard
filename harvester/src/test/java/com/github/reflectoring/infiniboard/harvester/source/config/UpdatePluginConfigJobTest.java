package com.github.reflectoring.infiniboard.harvester.source.config;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * Created by steven on 11.06.16.
 */
public class UpdatePluginConfigJobTest {

    @Test
    public void remembersModified() {
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        SchedulingService schedulingService = mock(SchedulingService.class);
        WidgetConfigRepository repository = mock(WidgetConfigRepository.class);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);
        when(applicationContext.getBean(WidgetConfigRepository.class)).thenReturn(repository);

        UpdatePluginConfigJob job = new UpdatePluginConfigJob();
        job.executeInternal(applicationContext, new JobKey("update", "harvester"), Collections.emptyMap());
        job.executeInternal(applicationContext, new JobKey("update", "harvester"), Collections.emptyMap());
        job.executeInternal(applicationContext, new JobKey("update", "harvester"), Collections.emptyMap());

        // first call searches all configurations
        verify(repository, atMost(1)).findAll();
        // subsequent calls search new configurations
        verify(repository, atLeast(2)).findAllByLastModifiedAfter(any(LocalDate.class));
    }

    @Test
    public void callsSchedulingService() throws SchedulerException, NoSuchFieldException, IllegalAccessException {
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        SchedulingService schedulingService = mock(SchedulingService.class);
        WidgetConfigRepository repository = mock(WidgetConfigRepository.class);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);
        when(applicationContext.getBean(WidgetConfigRepository.class)).thenReturn(repository);

        // making id accessible
        Field idField = WidgetConfig.class.getDeclaredField("id");
        idField.setAccessible(true);

        ArrayList<WidgetConfig> widgetConfigs = new ArrayList<>();

        String firstWidget = "firstWidget";
        WidgetConfig widgetConfig = new WidgetConfig(firstWidget);
        idField.set(widgetConfig, firstWidget);
        SourceConfig one = new SourceConfig("one", "TestJob", 500, Collections.emptyMap());
        widgetConfig.add(one);
        SourceConfig two = new SourceConfig("two", "TestJob", 500, Collections.emptyMap());
        widgetConfig.add(two);
        SourceConfig three = new SourceConfig("three", "TestJob", 500, Collections.emptyMap());
        widgetConfig.add(three);
        widgetConfigs.add(widgetConfig);

        String secondWidget = "secondWidget";
        widgetConfig = new WidgetConfig(secondWidget);
        idField.set(widgetConfig, secondWidget);
        SourceConfig alpha = new SourceConfig("alpha", "TestJob", 500, Collections.emptyMap());
        widgetConfig.add(alpha);
        SourceConfig beta = new SourceConfig("beta", "TestJob", 500, Collections.emptyMap());
        widgetConfig.add(beta);
        SourceConfig gamma = new SourceConfig("gamma", "TestJob", 500, Collections.emptyMap());
        widgetConfig.add(gamma);
        widgetConfigs.add(widgetConfig);
        when(repository.findAll()).thenReturn(widgetConfigs);

        UpdatePluginConfigJob job = new UpdatePluginConfigJob();
        job.executeInternal(applicationContext, new JobKey("update", "harvester"), new HashMap<>());

        verify(schedulingService).cancelJobs(firstWidget);
        verify(schedulingService).scheduleJob(firstWidget, one);
        verify(schedulingService).scheduleJob(firstWidget, two);
        verify(schedulingService).scheduleJob(firstWidget, three);

        verify(schedulingService).cancelJobs(secondWidget);
        verify(schedulingService).scheduleJob(secondWidget, alpha);
        verify(schedulingService).scheduleJob(secondWidget, beta);
        verify(schedulingService).scheduleJob(secondWidget, gamma);
    }

}