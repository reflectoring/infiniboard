package com.github.reflectoring.infiniboard.harvester;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.sourceConfig.ConfigJobManagerJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfigRepository;
import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * application class for harvester service
 */
@EnableMongoRepositories(basePackages = "com.github.reflectoring.infiniboard.packrat.source")
@SpringBootApplication
public class HarvesterApplication {

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    SourceConfigRepository sourceConfigRepository;

    @PostConstruct
    public void startScheduling() throws SchedulerException {

        schedulingService.scheduleJob("source", "harvester", ConfigJobManagerJob.class, 7);

        addTestSourceConfig();

        schedulingService.scheduleJob("source2", "harvester2", UpdatePluginConfigJob.class, 9);
    }

    public void addTestSourceConfig() {
        SourceConfig config = new SourceConfig("sc", "widget", new Date());
        List<UrlSource> urlSources = new ArrayList<>();
        urlSources.add(new UrlSource("www.foo1.bar", new Date(), 3));
        urlSources.add(new UrlSource("www.foo2.bar", new Date(), 4));
        urlSources.add(new UrlSource("www.foo3.bar", new Date(), 5));
        config.setUrlSources(urlSources);
        sourceConfigRepository.save(config);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }


}
