package com.github.reflectoring.infiniboard.harvester.scheduling;

import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SchedulingServiceTest {

    private static final String TEST_JOB = "TestJob";

    private static final String GROUP_NAME = "SchedulingServiceTest";

    private WidgetConfigRepository widgetConfigRepository;

    private SourceDataRepository sourceDataRepository;

    private SchedulingService schedulingService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup()
            throws SchedulerException {
        ApplicationContext applicationContext = mock(ApplicationContext.class);

        //introduce repositorys
        widgetConfigRepository = mock(WidgetConfigRepository.class);
        when(widgetConfigRepository.exists(GROUP_NAME)).thenReturn(true);
        when(applicationContext.getBean(WidgetConfigRepository.class)).thenReturn(widgetConfigRepository);

        sourceDataRepository = mock(SourceDataRepository.class);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);

        schedulingService = new SchedulingService(applicationContext, widgetConfigRepository, sourceDataRepository);
        schedulingService.setupScheduling();
        schedulingService.registerJob(TEST_JOB, TestJob.class);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);
    }

    @After
    public void cleanup()
            throws SchedulerException {
        schedulingService.cleanupScheduling();
    }

    @Test
    public void registerJobNotRegisteringTwice()
            throws SchedulerException {
        expectedException.expect(JobTypeAlreadyRegisteredException.class);
        schedulingService.registerJob(TEST_JOB, TestJob.class);
    }

    @Test
    public void sameJobCanNotBeScheduledTwice()
            throws SchedulerException {
        try {
            expectedException.expect(JobAlreadyScheduledException.class);
            schedulingService
                    .scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, Collections.emptyMap()));
            schedulingService
                    .scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, Collections.emptyMap()));
        } finally {
            schedulingService.cancelJobs(GROUP_NAME);
        }
    }

    @Test
    public void scheduleJobShouldRunAtLeastThreeTimes()
            throws SchedulerException, InterruptedException {
        MutableInt              mutableInt = new MutableInt(0);
        HashMap<String, Object> map        = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        Thread.sleep(500);
        schedulingService.cancelJobs(GROUP_NAME);
        assertThat(mutableInt.getValue()).isGreaterThan(3);
    }

    @Test
    public void cancelJob()
            throws SchedulerException, InterruptedException {
        MutableInt              mutableInt = new MutableInt(0);
        HashMap<String, Object> map        = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        assertTrue(schedulingService.checkJobExists(TEST_JOB, GROUP_NAME));

        schedulingService.cancelJobs(GROUP_NAME);
        assertFalse(schedulingService.checkJobExists(TEST_JOB, GROUP_NAME));
    }


    @Test
    public void sourceJobShouldBeCanceled()
            throws SchedulerException, InterruptedException {
        // this test should find no widget and therefore cancel the job
        when(widgetConfigRepository.exists(GROUP_NAME)).thenReturn(false);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, Collections.emptyMap()));
        Thread.sleep(200); //time for scheduling service to cancel the job

        assertFalse(schedulingService.checkJobExists(TEST_JOB, GROUP_NAME));
        verify(sourceDataRepository).deleteByWidgetId(eq(GROUP_NAME));
    }

}
