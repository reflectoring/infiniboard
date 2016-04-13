package com.github.reflectoring.infiniboard.packrat.source;

import com.github.reflectoring.infiniboard.packrat.OverseerTestApplication;
import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;
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
@SpringApplicationConfiguration(classes = OverseerTestApplication.class)
@Category(MongoIntegrationTests.class)
public class SourceConfigTest {

    @Autowired
    private SourceConfigRepository repository;

    @Test
    public void variableConfig() {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("alpha", "one");
        attributes.put("beta", "two");
        attributes.put("gamma", "three");
        SourceConfig config = new SourceConfig("foo", "bar", new Date(), 5 * 60, attributes);

        repository.save(config);

        List<SourceConfig> configList = repository.findBySourceId("bar");
        assertThat(configList).hasSize(1);
        SourceConfig sourceConfig = configList.get(0);
        assertThat(sourceConfig.getConfigData()).containsKeys("alpha", "beta", "gamma");
    }

}