package com.github.reflectoring.infiniboard.quartermaster;

import com.github.reflectoring.infiniboard.quartermaster.testframework.ServiceMockConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring boot application class that does not scan for MongoDB repositories to enable tests of REST
 * controllers that mock away the service layer.
 */
@SpringBootApplication
@Configuration
@Import(ServiceMockConfiguration.class)
public class ControllerTestApp {

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(ControllerTestApp.class).run(args);
  }
}
