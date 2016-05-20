package com.github.reflectoring;

import java.util.HashMap;
import java.util.Map;

public class HalJsonResource {

//    private List<HalJsonResource> _embedded;
//
//    private List<Link> _links;

    private Map<String, Object> properties;

    public HalJsonResource() {
//        this._embedded = new ArrayList<>();
//        this._links = new ArrayList<>();
        this.properties = new HashMap<>();
    }

    public void addProperty(String name, Object object) {
        checkPropertyNotExists(name);

        this.properties.put(name, object);
    }

    private void checkPropertyNotExists(String name) {
        if (properties.containsKey(name)) {
            throw new IllegalArgumentException();
        }
    }

    public Object getProperty(String name) {
        if (!(properties.containsKey(name))) {
            throw new IllegalArgumentException();
        }

        return this.properties.get(name);
    }
}
