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

import java.util.Date;
import java.util.HashMap;
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

    @Before
    public void setup() {
        HashMap<Integer, ConfigSource> attributes = new HashMap<>();
        attributes.put(new Integer(1), new UrlSource("http://www.foo.de", new Date(), null, 0, 5));
        attributes.put(new Integer(2), new UrlSource("http://www.foo.de", new Date(), null, 0, 5));
        attributes.put(new Integer(3), new UrlSource("http://www.foo.de", new Date(), null, 0, 5));

        SourceConfig config = new SourceConfig("foo", "bar", new Date(), 5 * 60, attributes);

        repository.save(config);
    }

    @Test
    public void configWasSaved() {
        List<SourceConfig> configList = repository.findBySourceId("bar");
        assertThat(configList).hasSize(1);
    }

    @Test
    public void variableConfig() {
        List<SourceConfig> configList = repository.findBySourceId("bar");
        SourceConfig sourceConfig = configList.get(0);
        assertThat(sourceConfig.getConfigData()).containsKeys(new Integer(1), new Integer(2), new Integer(3));
    }

}