package com.github.reflectoring.haljson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.reflectoring.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HalJsonResource extends Json {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    private Map<String, List<HalJsonResource>> embedded;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_links")
    private Map<String, List<Link>> links;

    public HalJsonResource() {
        super();
        this.embedded = new HashMap<>();
        this.links = new HashMap<>();
    }

    public void add(Link link) {
        checkLinkRelNotExists(link.getRel());
        checkLinkNotExists(link);

        List<Link> links = this.links.get(link.getRel());
        if (links == null) {
            links = new ArrayList<>();
        }

        links.add(link);
        this.links.put(link.getRel(), links);
    }

    private void checkLinkRelNotExists(String rel) {

        this.links.keySet().forEach(key -> {
            if (key.equalsIgnoreCase(rel)) {
                throw new IllegalArgumentException("A link with the same relation already exists.");
            }
        });
    }

    private void checkLinkNotExists(Link link) {
        this.links.values().forEach(l -> {
            if (l.equals(link)) {
                throw new IllegalArgumentException("This link is already added.");
            }
        });
    }

    public void add(String rel, HalJsonResource embeddedResource) {

        checkEmbeddedRelNotExists(rel);
        checkEmbeddedResourceNotExists(embeddedResource);

        List<HalJsonResource> embeddedResources = this.embedded.get(rel);
        if (embeddedResources == null) {
            embeddedResources = new ArrayList<>();
        }
        embeddedResources.add(embeddedResource);
        this.embedded.put(rel, embeddedResources);
    }

    private void checkEmbeddedResourceNotExists(HalJsonResource embeddedResource) {
        this.embedded.values().forEach(resource -> {
            if (resource.equals(embeddedResource)) {
                throw new IllegalArgumentException("This embedded resource is already added.");
            }
        });
    }

    private void checkEmbeddedRelNotExists(String rel) {
        this.embedded.keySet().forEach(k -> {
            if (k.equalsIgnoreCase(rel)) {
                throw new IllegalArgumentException("An embedded resource with this relation already exists.");
            }
        });
    }
}
