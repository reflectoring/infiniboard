package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class UrlSource {

    @Id
    private String id;

    private int updateInterval;

    private String url;

    private Date lastFetched;

    private int statusCode;

    public UrlSource(String url, Date lastFetched, int updateInterval) {
        this.url = url;
        this.lastFetched = lastFetched;
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

    public Date getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(Date lastFetched) {
        this.lastFetched = lastFetched;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
