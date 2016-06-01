package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

public class UrlSource {

    @Id
    private String id;

    private int updateInterval;

    private String url;

    private int statusCode;

    public UrlSource(String url, int updateInterval) {
        this.url = url;
        this.updateInterval = updateInterval;
    }

    public String getId() {
        return id;
    }

    public int getUpdateInterval() {
        return this.updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public String getUrl() {
        return url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
