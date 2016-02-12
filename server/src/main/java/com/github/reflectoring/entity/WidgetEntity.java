package com.github.reflectoring.entity;


import org.springframework.data.annotation.Id;

public class WidgetEntity {

    @Id
    private String id;

    private WidgetConfiguration configuration;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WidgetConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(WidgetConfiguration configuration) {
        this.configuration = configuration;
    }
}
