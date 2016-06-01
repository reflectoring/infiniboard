package com.github.reflectoring.infiniboard.packrat.source;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Widget {

    @Id
    private String id;

    @DBRef
    SourceConfig sourceConfig;

    public Widget(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }

    public String getId() {
        return id;
    }
}
