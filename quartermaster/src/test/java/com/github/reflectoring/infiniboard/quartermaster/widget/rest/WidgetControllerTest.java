package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.WidgetConfigFactory.widgetConfig;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WidgetControllerTest extends ControllerTestTemplate {

    @Autowired
    private WidgetConfigService widgetConfigService;

    @Test
    public void getWidget()
            throws Exception {
        when(widgetConfigService.loadWidget("1")).thenReturn(widgetConfig());
        MvcResult result = mvc().perform(get("/api/widgets/1"))
                .andExpect(status().isOk())
                .andExpect(containsResource(HalJsonResource.class))
                .andReturn();
    }

}