package com.github.reflectoring.infiniboard.harvester.scheduling.config;

import java.lang.reflect.Field;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdatePluginConfigJobTest {

    private ApplicationContext applicationContext;

    private SchedulingService schedulingService;

    private WidgetConfigRepository repository;

    @Before
    public void initializeMocks() {
        applicationContext = mock(ApplicationContext.class);
        schedulingService = mock(SchedulingService.class);
        repository = mock(WidgetConfigRepository.class);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);
        when(applicationContext.getBean(WidgetConfigRepository.class)).thenReturn(repository);
    }

    @Test
    public void executeInternalRemembersLastFetchDate() {
        //
        //        UpdateSourceConfigJob job = new UpdateSourceConfigJob();
        //        job.executeInternal(applicationContext, new JobKey("update", "harvester"), Collections.emptyMap());
        //        job.executeInternal(applicationContext, new JobKey("update", "harvester"), Collections.emptyMap());
        //        job.executeInternal(applicationContext, new JobKey("update", "harvester"), Collections.emptyMap());
        //
        //        // first call searches all configurations
        //        verify(repository, atMost(1)).findAll();
        //        // subsequent calls search new configurations
        //        verify(repository, atLeast(2)).findAllByLastModifiedAfter(any(LocalDateTime.class));
    }

    @Test
    public void executeInternalSchedulesAllGivenJobs()
            throws SchedulerException, NoSuchFieldException, IllegalAccessException {

        //        ArrayList<WidgetConfig> widgetConfigs = new ArrayList<>();
        //        when(repository.findAll()).thenReturn(widgetConfigs);
        //
        //        widgetConfigs.add(createWidgetConfig("firstWidget", "one", "two", "three"));
        //        widgetConfigs.add(createWidgetConfig("secondWidget", "alpha", "beta", "gamma"));
        //
        //        UpdateSourceConfigJob job = new UpdateSourceConfigJob();
        //        job.executeInternal(applicationContext, new JobKey("update", "harvester"), new HashMap<>());
        //
        //        for (WidgetConfig widget : widgetConfigs) {
        //            // cleanup old jobs
        //            verify(schedulingService).cancelJobs(widget.getId());
        //            for (SourceConfig source : widget.getSourceConfigs()) {
        //                // schedule new jobs
        //                verify(schedulingService).scheduleJob(widget.getId(), source);
        //            }
        //        }

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
