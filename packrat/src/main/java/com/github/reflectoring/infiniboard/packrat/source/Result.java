package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;


public abstract class Result {

    @Id
    private String id;

    private int sourceId;

    private String content;

    public Result(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getId() {
        return id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
