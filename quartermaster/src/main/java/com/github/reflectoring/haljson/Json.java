package com.github.reflectoring.haljson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Json {

    private Map<String, Object> properties;

    public Json() {
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

    @JsonAnySetter
    public void add(String key, Object value) {
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return properties;
    }
}
