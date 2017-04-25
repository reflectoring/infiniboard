package com.github.reflectoring.infiniboard.quartermaster.testframework.factory;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.quartermaster.widget.rest.WidgetConfigResource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WidgetConfigFactory {

  public static List<WidgetConfig> widgetConfigList() {
    ArrayList<WidgetConfig> list = new ArrayList<>();
    list.add(widgetConfig());
    return list;
  }

  public static WidgetConfig widgetConfig() {
    WidgetConfig widgetConfig = new WidgetConfig();
    widgetConfig.setLastModified(LocalDateTime.now());
    widgetConfig.setTitle("My Little Widget");
    widgetConfig.setId("my_little_widget");
    widgetConfig.setType("platform-status");
    return widgetConfig;
  }

  public static WidgetConfigResource widgetConfigResource() {
    WidgetConfigResource widgetConfigResource = new WidgetConfigResource();
    widgetConfigResource.setTitle("My Little Widget");
    widgetConfigResource.setLastModified(new Date());
    widgetConfigResource.setType("platform-status");
    return widgetConfigResource;
  }
}
