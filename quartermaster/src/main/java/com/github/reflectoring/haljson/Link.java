package com.github.reflectoring.haljson;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Link {

    @JsonIgnore
    private String rel;

    private String href;

    public Link(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
