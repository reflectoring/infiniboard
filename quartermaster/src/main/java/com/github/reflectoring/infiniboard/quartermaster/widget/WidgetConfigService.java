package com.github.reflectoring.infiniboard.quartermaster.widget;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import com.github.reflectoring.model.Widget;
import com.github.reflectoring.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WidgetConfigService {

    private WidgetConfigRepository widgetConfigRepository;

    private SourceDataRepository sourceDataRepository;

    @Autowired
    public WidgetConfigService(WidgetConfigRepository widgetConfigRepository, SourceDataRepository sourceDataRepository) {
        this.widgetConfigRepository = widgetConfigRepository;
        this.sourceDataRepository = sourceDataRepository;
    }

    public WidgetConfig loadWidget(String widgetId) {
        return widgetConfigRepository.findOne(widgetId);
    }

    public WidgetConfig saveWidget(WidgetConfig widgetConfig) {
        widgetConfig.setLastModified(LocalDate.now());
        return widgetConfigRepository.save(widgetConfig);
    }

    public List<SourceData> getData(String widgetId) {
        return sourceDataRepository.findAllByWidgetId(widgetId);
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
