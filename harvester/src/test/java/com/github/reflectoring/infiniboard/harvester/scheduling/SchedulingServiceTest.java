package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SchedulingServiceTest {

    private static final String TEST_JOB = "TestJob";

    private static final String GROUP_NAME = "SchedulingServiceTest";

    private WidgetConfigRepository widgetConfigRepository;

    private SourceDataRepository sourceDataRepository;

    private SchedulingService schedulingService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() throws SchedulerException {
        ApplicationContext applicationContext = mock(ApplicationContext.class);

        //introduce repositorys
        widgetConfigRepository = mock(WidgetConfigRepository.class);
        sourceDataRepository = mock(SourceDataRepository.class);

        schedulingService = new SchedulingService(applicationContext, widgetConfigRepository, sourceDataRepository);
        when(applicationContext.getBean(SchedulingService.class)).thenReturn(schedulingService);

        schedulingService.registerJob(TEST_JOB, TestJob.class);
    }

    @Test
    public void registerJobNotRegisteringTwice() throws SchedulerException {
        expectedException.expectMessage("job type TestJob is already registered by " + TestJob.class.toString());
        schedulingService.registerJob(TEST_JOB, TestJob.class);
    }

    @Test
    public void sameJobCanNotBeScheduledTwice() throws SchedulerException{
        expectedException.expectMessage("Job already exists");
        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, Collections.emptyMap()));

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, Collections.emptyMap()));
        schedulingService.cancelJobs(GROUP_NAME);
    }

    @Test
    public void scheduleJobShouldRunAtLeastThreeTimes() throws SchedulerException, InterruptedException {
        when(widgetConfigRepository.exists(GROUP_NAME)).thenReturn(true);

        MutableInt mutableInt = new MutableInt(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        Thread.sleep(500);
        schedulingService.cancelJobs(GROUP_NAME);
        assertThat(mutableInt.getValue()).isGreaterThan(3);
    }

    @Test
    public void cancelJob() throws SchedulerException, InterruptedException {
        MutableInt mutableInt = new MutableInt(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        Thread.sleep(100); //time for scheduling service to cancel the job

        assertTrue(schedulingService.checkJobExists(TEST_JOB, GROUP_NAME));

        schedulingService.cancelJobs(GROUP_NAME);
        assertFalse(schedulingService.checkJobExists(TEST_JOB, GROUP_NAME));
    }


    @Test
    public void souceJobShouldBeCanceled() throws SchedulerException, InterruptedException {
        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, Collections.emptyMap()));
        Thread.sleep(200); //time for scheduling service to cancel the job

        assertFalse(schedulingService.checkJobExists(TEST_JOB, GROUP_NAME));
        verify(sourceDataRepository).deleteByWidgetId(eq(GROUP_NAME));
    }

}
