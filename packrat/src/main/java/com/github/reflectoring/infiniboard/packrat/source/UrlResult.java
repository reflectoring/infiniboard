package com.github.reflectoring.infiniboard.packrat.source;

public class UrlResult extends Result {

    private String content;

    public UrlResult(int sourceId) {
        super(sourceId);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
