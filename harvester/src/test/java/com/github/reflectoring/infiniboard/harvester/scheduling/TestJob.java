package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableInt;
import org.quartz.JobKey;
import org.springframework.context.ApplicationContext;

/** dummy job incrementing an MutableInt given by configuration, used for scheduling tests */
public class TestJob extends SourceJob {

  static final String COUNTER = "counter";

  @Override
  protected void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration) {
    MutableInt counter = (MutableInt) configuration.get(COUNTER);
    counter.increment();
  }
}
