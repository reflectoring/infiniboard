package com.github.reflectoring.service;

import com.github.reflectoring.model.Widget;
import com.github.reflectoring.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WidgetService {

    private WidgetRepository widgetRepository;

    @Autowired
    public WidgetService(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    public List<Widget> loadWidgets() {
        return Arrays.asList(getMockWidget());
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
