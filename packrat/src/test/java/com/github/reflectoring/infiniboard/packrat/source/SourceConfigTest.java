package com.github.reflectoring.infiniboard.packrat.source;

import com.github.reflectoring.infiniboard.packrat.PackratTestApplication;
import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * testing the source configurations
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PackratTestApplication.class)
@Category(MongoIntegrationTests.class)
public class SourceConfigTest {

    @Autowired
    private SourceConfigRepository repository;


    private String testSourceConfigId;

    @Before
    public void setup() {
        SourceConfig sourceConfig1 = new SourceConfig("sc", "widget1", new Date());

        List<UrlSource> urlSources = new ArrayList<>();
        urlSources.add(new UrlSource("www.foo1.bar", new Date(), 5));
        urlSources.add(new UrlSource("www.foo2.bar", new Date(), 5));
        urlSources.add(new UrlSource("www.foo3.bar", new Date(), 5));
        sourceConfig1.setUrlSources(urlSources);

        SourceConfig tempSourceconfig = repository.save(sourceConfig1);
        testSourceConfigId = tempSourceconfig.getId();
    }

    @Test
    public void findByWidgetIdTest() {
        SourceConfig result1 = repository.findById(testSourceConfigId);
        assertThat(result1).isNotNull();
    }

    @Test
    public void configWasSaved() {
        SourceConfig result1 = repository.findById(testSourceConfigId);
        assertThat(result1.getUrlSources()).hasSize(3);
    }
}
