package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

public class UrlResult extends Result {

    @Id
    private String id;

    private String content;

    public UrlResult(int sourceId) {
        super(sourceId);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
