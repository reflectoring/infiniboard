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
    private SourceConfigRepository sourceConfigRepository;

    @Autowired
    private UrlSourceRepository urlSourceRepository;


    private String testSourceConfigId;

    @Before
    public void setup() {
        SourceConfig sourceConfig1 = new SourceConfig("widget1");

        List<UrlSource> urlSources = new ArrayList<>();
        urlSources.add(new UrlSource("www.foo1.bar", 2));
        urlSources.add(new UrlSource("www.foo2.bar", 3));
        urlSources.add(new UrlSource("www.foo3.bar", 6));
        sourceConfig1.setUrlSources(urlSources);
        urlSources = urlSourceRepository.save(urlSources);
        sourceConfig1 = sourceConfigRepository.save(sourceConfig1);
        testSourceConfigId = sourceConfig1.getId();
    }

    @Test
    public void sourceConfigSavedTest() {
        SourceConfig sourceConfig = sourceConfigRepository.findOne(testSourceConfigId);
        assertThat(sourceConfig).isNotNull();
        assertThat(sourceConfig.getUrlSources()).hasSize(3);
    }

    @Test
    public void configWasSaved() {
    }
}
