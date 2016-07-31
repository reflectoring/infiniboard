package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DashboardControllerTest extends ControllerTestTemplate {

    @Autowired
    private WidgetConfigService widgetConfigService;

    @Test
    public void getAllDashboardConfigurations()
            throws Exception {
        MvcResult result = mvc().perform(get("/api/dashboards"))
                .andExpect(status().isOk())
                .andExpect(containsResource(List.class))
                .andReturn();
    }

    @Test
    public void getDashboardConfiguration()
            throws Exception {
        MvcResult result = mvc().perform(get("/api/dashboards/1"))
                .andExpect(status().isOk())
                .andExpect(containsResource(HalJsonResource.class))
                .andReturn();
    }

}