package com.github.reflectoring.infiniboard.packrat.source;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.reflectoring.infiniboard.packrat.PackratTestApplication;
import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;

/**
 * tests embedding of source config in widget config
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PackratTestApplication.class)
@Category(MongoIntegrationTests.class)
public class WidgetConfigTest {

    public static final String TITLE = "myFirstWidget";
    public static final String UPDATED_WIDGET = "updatedWidget";

    @Autowired
    private WidgetConfigRepository repository;

    @Before
    public void setup() {
        WidgetConfig widget = new WidgetConfig(TITLE);
        widget.setLastModified(LocalDate.now().minusDays(5));
        widget.add(new SourceConfig("1", "url", 500, new HashMap<String, Object>()));
        widget.add(new SourceConfig("2", "jenkins", 500, new HashMap<String, Object>()));
        widget.add(new SourceConfig("3", "sonar", 500, new HashMap<String, Object>()));
        repository.save(widget);

        widget = new WidgetConfig(UPDATED_WIDGET);
        widget.setLastModified(LocalDate.now());
        widget.add(new SourceConfig("1", "url", 500, new HashMap<String, Object>()));
        widget.add(new SourceConfig("2", "jenkins", 500, new HashMap<String, Object>()));
        widget.add(new SourceConfig("3", "sonar", 500, new HashMap<String, Object>()));
        repository.save(widget);
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void firstEmbeddedSource() {
        WidgetConfig widgetConfig = repository.findByTitle(TITLE);
        assertThat(widgetConfig.getSourceConfigs().get(0)).hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("sourceId", "url");
    }

    @Test
    public void generatedId() {
        WidgetConfig widgetConfig = repository.findByTitle(TITLE);
        assertThat(widgetConfig.getId()).isNotEmpty();
    }

    @Test
    public void lastModified() {
        List<WidgetConfig> widgetConfigs = repository.findByLastModifiedAfter(LocalDate.now().minusDays(3));
        assertThat(widgetConfigs).hasSize(1);
        assertThat(widgetConfigs.get(0)).hasFieldOrPropertyWithValue("title", UPDATED_WIDGET);
    }

}
