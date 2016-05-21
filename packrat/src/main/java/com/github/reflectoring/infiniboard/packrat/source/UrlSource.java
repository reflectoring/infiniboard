package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * information about an url request
 */
public class UrlSource extends ConfigSource{

    private String url;

    private Date lastFetched;

    private String content;

    private int statusCode;

    public UrlSource(String url, Date lastFetched, String content, int statusCode, int updateInterval ) {
        super(updateInterval);
        this.url = url;
        this.lastFetched = lastFetched;
        this.content = content;
        this.statusCode = statusCode;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
