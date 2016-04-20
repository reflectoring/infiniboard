package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * information about an url request
 */
public class UrlSource {

    @Id
    private String url;

    private Date lastFetched;

    private String content;

    private Integer statusCode;

    public UrlSource(String url, Date lastFetched, String content, Integer statusCode) {
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

    public String getContent() {
        return content;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
