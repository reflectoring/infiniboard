package com.github.reflectoring.infiniboard.packrat.dashboard;

import org.springframework.data.annotation.Id;

/**
 * dashboard configuration
 */
public class DashboardConfig {

    @Id
    private String id;

    private String title;

    public DashboardConfig(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
