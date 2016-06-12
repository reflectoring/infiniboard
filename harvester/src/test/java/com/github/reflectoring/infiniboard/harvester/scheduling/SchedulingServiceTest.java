package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.quartz.*;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests the scheduling service by using TestJob
 */
public class SchedulingServiceTest {

    public static final String TEST_JOB = "TestJob";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void notRegisteringTwice() throws SchedulerException {
        SchedulingService schedulingService = new SchedulingService();
        schedulingService.registerJob(TEST_JOB, TestJob.class);
        expectedException.expectMessage("job type TestJob is already registered by " + TestJob.class.toString());
        schedulingService.registerJob(TEST_JOB, TestJob.class);
    }

    @Test
    public void scheduleJob() throws SchedulerException, InterruptedException {
        SchedulingService schedulingService = new SchedulingService();
        schedulingService.registerJob(TEST_JOB, TestJob.class);
        MutableInt mutableInt = new MutableInt(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);
        String groupName = "SchedulingServiceTest";
        schedulingService.scheduleJob(groupName, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        Thread.sleep(500);
        schedulingService.cancelJobs(groupName);
        assertThat(mutableInt.getValue()).isGreaterThan(3);
    }

    @Test
    public void cancelJob() throws SchedulerException {
        SchedulingService schedulingService = new SchedulingService();
        schedulingService.registerJob(TEST_JOB, TestJob.class);
        MutableInt mutableInt = new MutableInt(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestJob.COUNTER, mutableInt);
        String groupName = "SchedulingServiceTest";
        schedulingService.scheduleJob(groupName, new SourceConfig(TEST_JOB, TEST_JOB, 100, map));
        assertTrue(schedulingService.checkExists(TEST_JOB, groupName));
        schedulingService.cancelJobs(groupName);
        assertFalse(schedulingService.checkExists(TEST_JOB, groupName));
    }

}