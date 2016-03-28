package com.github.reflectoring.infiniboard.harvester.source.url;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * information about an url request
 */
public class UrlData {

    @Id
    private String url;

    private Date lastFetched;

    private String content;

    private Integer statuscode;

    public UrlData(String url, Date lastFetched, String content, Integer statuscode) {
        this.url = url;
        this.lastFetched = lastFetched;
        this.content = content;
        this.statuscode = statuscode;
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

    public Integer getStatuscode() {
        return statuscode;
    }
}
