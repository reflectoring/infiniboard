package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SchedulingServiceTest {

    private static final String TEST_JOB = "TestJob";

    private static final String GROUP_NAME = "SchedulingServiceTest";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SchedulingService getSchedulingService() throws SchedulerException {
        SchedulingService schedulingService = new SchedulingService(mock(ApplicationContext.class));
        schedulingService.registerJob(TEST_JOB, TestJob.class);
        return schedulingService;
    }

    @Test
    public void registerJobNotRegisteringTwice() throws SchedulerException {
        SchedulingService schedulingService = getSchedulingService();
        expectedException.expectMessage("job type TestJob is already registered by " + TestJob.class.toString());
        schedulingService.registerJob(TEST_JOB, TestJob.class);
    }

    @Test
    public void sameJobCanNotBeScheduledTwice() throws SchedulerException{
        SchedulingService schedulingService = getSchedulingService();
        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, new HashMap<String, Object>()));

        expectedException.expectMessage("Job already exists");
        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, new HashMap<String, Object>()));
    }

    @Test
    public void scheduleJobShouldRunAtLeastThreeTimes() throws SchedulerException, InterruptedException {
        SchedulingService schedulingService = getSchedulingService();

        MutableInt mutableInt = new MutableInt(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        Thread.sleep(500);
        schedulingService.cancelJobs(GROUP_NAME);
        assertThat(mutableInt.getValue()).isGreaterThan(3);
    }

    @Test
    public void cancelJob() throws SchedulerException {
        SchedulingService schedulingService = getSchedulingService();

        MutableInt mutableInt = new MutableInt(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);

        schedulingService.scheduleJob(GROUP_NAME, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        assertTrue(schedulingService.checkExists(TEST_JOB, GROUP_NAME));

        schedulingService.cancelJobs(GROUP_NAME);
        assertFalse(schedulingService.checkExists(TEST_JOB, GROUP_NAME));
    }

    @Test
    public void canSourceJobBeExecuted(){
        //TODO positive and negative test
    }

}
