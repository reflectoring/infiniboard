package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class UrlResult {

    @Id
    private String id;

    @DBRef
    private UrlSource urlSource;

    private String content;

    public UrlResult(UrlSource urlSource) {
        this.urlSource = urlSource;
    }

    public UrlSource getUrlSource() {
        return urlSource;
    }

    public void setUrlSource(UrlSource urlSource) {
        this.urlSource = urlSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
