package com.github.reflectoring.infiniboard.packrat.source;

import java.time.LocalDateTime;
import java.util.Collections;
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
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PackratTestApplication.class)
@Category(MongoIntegrationTests.class)
public class WidgetConfigTest {

    private static final String TITLE          = "myFirstWidget";
    private static final String UPDATED_WIDGET = "updatedWidget";

    @Autowired
    private WidgetConfigRepository repository;

    @Before
    public void setup() {
        WidgetConfig widget = new WidgetConfig(TITLE);
        widget.setLastModified(LocalDateTime.now().minusDays(5));
        widget.add(new SourceConfig("1", "TestSource", 500, Collections.emptyMap()));
        widget.add(new SourceConfig("2", "TestSource", 500, Collections.emptyMap()));
        widget.add(new SourceConfig("3", "TestSource", 500, Collections.emptyMap()));
        repository.save(widget);

        widget = new WidgetConfig(UPDATED_WIDGET);
        widget.setLastModified(LocalDateTime.now());
        widget.add(new SourceConfig("1", "TestSource", 500, Collections.emptyMap()));
        widget.add(new SourceConfig("2", "TestSource", 500, Collections.emptyMap()));
        widget.add(new SourceConfig("3", "TestSource", 500, Collections.emptyMap()));
        repository.save(widget);
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void findByTitleReturnsWidgetConfigWithSourceConfigOfSavedType() {
        WidgetConfig widgetConfig = repository.findByTitle(TITLE);
        assertThat(widgetConfig.getSourceConfigs().get(0)).hasFieldOrPropertyWithValue("id", "1")
                .hasFieldOrPropertyWithValue("type", "TestSource");
    }

    @Test
    public void generatedId() {
        WidgetConfig widgetConfig = repository.findByTitle(TITLE);
        assertThat(widgetConfig.getId()).isNotEmpty();
    }

    @Test
    public void findAllByLastModifiedAfter() {
        List<WidgetConfig> widgetConfigs = repository.findAllByLastModifiedAfter(LocalDateTime.now().minusDays(3));
        assertThat(widgetConfigs).hasSize(1);
        assertThat(widgetConfigs.get(0)).hasFieldOrPropertyWithValue("title", UPDATED_WIDGET);
    }

}
