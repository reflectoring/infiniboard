package com.github.reflectoring.infiniboard.quartermaster.testframework;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.DashboardService;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that provides mock objects for all services to enable unit tests of the layer
 * above the services.
 */
@Configuration
public class ServiceMockConfiguration {

  @Bean
  public DashboardService dashboardService() {
    return Mockito.mock(DashboardService.class);
  }

  @Bean
  public WidgetConfigService widgetConfigService() {
    return Mockito.mock(WidgetConfigService.class);
  }

  @Bean
  public WidgetConfigRepository widgetConfigRepository() {
    return Mockito.mock(WidgetConfigRepository.class);
  }
}
