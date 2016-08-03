package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;

public class SourceDataResource extends ResourceSupport {

    private List<SourceData> sourceData = new ArrayList<>();

    public List<SourceData> getSourceData() {
        return sourceData;
    }

    public void setSourceData(List<SourceData> sourceData) {
        this.sourceData = sourceData;
    }
}
