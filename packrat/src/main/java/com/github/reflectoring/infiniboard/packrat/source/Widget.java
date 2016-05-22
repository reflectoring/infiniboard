package com.github.reflectoring.infiniboard.packrat.source;


import org.springframework.data.annotation.Id;

public class Widget {

    @Id
    private String id;

    public Widget() {

    }

    public String getId() {
        return id;
    }
}
