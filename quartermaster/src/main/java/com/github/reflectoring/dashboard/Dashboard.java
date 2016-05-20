package com.github.reflectoring.dashboard;

import com.github.reflectoring.HalJsonResource;

public class Dashboard {

    private int id;
    private String name;
    private String description;

    public Dashboard(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public HalJsonResource toResource() {
        HalJsonResource resource = new HalJsonResource();
        resource.addProperty("id", this.getId());
        resource.addProperty("name", this.getName());
        resource.addProperty("description", this.getDescription());
        return resource;
    }
}
