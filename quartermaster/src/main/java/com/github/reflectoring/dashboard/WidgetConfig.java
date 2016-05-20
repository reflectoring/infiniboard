package com.github.reflectoring.dashboard;

public class WidgetConfig {

    private int id;
    private String type;
    private int interval;

    public WidgetConfig(int id, String type, int interval) {
        this.id = id;
        this.type = type;
        this.interval = interval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
