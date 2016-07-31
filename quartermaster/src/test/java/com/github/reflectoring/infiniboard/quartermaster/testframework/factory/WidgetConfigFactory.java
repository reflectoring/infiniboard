package com.github.reflectoring.infiniboard.quartermaster.testframework.factory;

import java.time.LocalDateTime;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;

public class WidgetConfigFactory {

    public static WidgetConfig widgetConfig() {
        WidgetConfig widgetConfig = new WidgetConfig();
        widgetConfig.setLastModified(LocalDateTime.now());
        widgetConfig.setTitle("My Little Widget");
        return widgetConfig;
    }

}
