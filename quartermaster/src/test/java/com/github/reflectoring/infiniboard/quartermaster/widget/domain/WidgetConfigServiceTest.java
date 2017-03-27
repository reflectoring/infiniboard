package com.github.reflectoring.infiniboard.quartermaster.widget.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WidgetConfigServiceTest {

  @Mock private WidgetConfigRepository widgetConfigRepositoryMock;

  @InjectMocks private WidgetConfigService widgetConfigService;

  @Test
  public void saveWidgetUpdatesLastModified() {
    // given
    WidgetConfig widgetConfig = new WidgetConfig();
    widgetConfig.setType("divider");

    // when
    widgetConfigService.saveWidget(widgetConfig);

    // then
    assertThat(widgetConfig.getLastModified()).isNotNull();
  }
}
