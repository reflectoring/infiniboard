package com.github.reflectoring.infiniboard.harvester;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.harvester.source.sourceConfig.ConfigJobManagerJob;
import com.github.reflectoring.infiniboard.packrat.source.*;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    @Autowired
    UrlSourceRepository urlSourceRepository;

    @Autowired
    UrlResultRepository resultRepository;

    @PostConstruct
    public void startScheduling() throws SchedulerException {

        schedulingService.scheduleJob("source", "harvester", ConfigJobManagerJob.class, 30);

        addTestSourceConfig();

        schedulingService.scheduleJob("source2", "harvester2", UpdatePluginConfigJob.class, 5);
    }

    public void addTestSourceConfig() {
        SourceConfig sourceConfig = new SourceConfig("widget");
        List<UrlSource> urlSources = new ArrayList<>();
        urlSources.add(new UrlSource("www.foo1.bar", 3));
        urlSources.add(new UrlSource("www.foo2.bar", 4));
        urlSources.add(new UrlSource("www.foo3.bar", 5));
        urlSources = urlSourceRepository.save(urlSources);

        sourceConfig.setUrlSources(urlSources);
        sourceConfigRepository.save(sourceConfig);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HarvesterApplication.class);
    }
}
