package com.github.reflectoring.controller;

import com.github.reflectoring.model.Widget;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/mock")
@RestController
public class MockController {

    @RequestMapping(value = "/buildWidget", method = RequestMethod.GET)
    public Widget mockWidget() {
        return getMockWidget();
    }

    private Widget getMockWidget() {

        Widget widget = new Widget();
        widget.setName("Build");
        widget.setConfiguration(getBuildConfiguration());
        widget.setValue(getJobValues());

        return widget;
    }

    private Map<String, Object> getBuildConfiguration() {
        HashMap<String, Object> root = new HashMap<>();

        HashMap<Object, Object> config = new HashMap<>();
        root.put("configuration", config);

        HashMap<Object, Object> jobs = new HashMap<>();
        config.put("jobs", jobs);


        Map<String, Object> job1 = createJob("Jenkins Job DSL", "https://jenkins.ci.cloudbees.com/job/plugins/job/job-dsl-plugin");
        jobs.put("job", job1);

        return root;
    }

    private Map<String, Object> createJob(String name, String url) {
        Map<String, Object> job1 = new HashMap<>();
        job1.put("id", 1L);
        job1.put("name", name);
        job1.put("url", url);

        return job1;
    }

    private Map<String, Object> getJobValues() {
        Map<String, Object> values = new HashMap<>();
        Map<String, Object> job1 = createJobValues(1L, "green");
        values.put("values", job1);

        return values;
    }

    private Map<String, Object> createJobValues(long id, String status) {
        Map<String, Object> job1 = new HashMap<>();
        job1.put("id", id);
        job1.put("status", status);
        return job1;
    }
}
