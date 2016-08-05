package com.github.reflectoring.infiniboard.quartermaster.testframework.factory;

import java.time.LocalDateTime;
import java.util.Date;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.quartermaster.widget.rest.WidgetConfigResource;

public class WidgetConfigFactory {

    public static WidgetConfig widgetConfig() {
        WidgetConfig widgetConfig = new WidgetConfig();
        widgetConfig.setLastModified(LocalDateTime.now());
        widgetConfig.setTitle("My Little Widget");
        widgetConfig.setId("my_little_widget");
        return widgetConfig;
    }

    public static WidgetConfigResource widgetConfigResource() {
        WidgetConfigResource widgetConfigResource = new WidgetConfigResource();
        widgetConfigResource.setTitle("My Little Widget");
        widgetConfigResource.setLastModified(new Date());
        return widgetConfigResource;
    }

}
