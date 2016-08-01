package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import org.springframework.hateoas.ResourceSupport;

public class DashboardResource extends ResourceSupport {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
