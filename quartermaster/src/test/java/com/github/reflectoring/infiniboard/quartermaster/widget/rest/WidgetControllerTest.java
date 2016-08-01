package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.fromJson;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.toJsonWithoutLinks;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.SourceDataFactory.sourceDataList;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.WidgetConfigFactory.widgetConfig;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.WidgetConfigFactory.widgetConfigResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WidgetControllerTest extends ControllerTestTemplate {

    @Autowired
    private WidgetConfigService widgetService;

    @Test
    public void getWidget()
            throws Exception {
        when(widgetService.loadWidget("my_little_widget")).thenReturn(widgetConfig());
        MvcResult result = mvc().perform(get("/api/dashboards/1/widgets/my_little_widget"))
                .andExpect(status().isOk())
                .andExpect(containsResource(WidgetConfigResource.class))
                .andReturn();

        WidgetConfigResource resource = fromJson(result.getResponse().getContentAsString(), WidgetConfigResource.class);
        assertThat(resource.getTitle()).isEqualTo("My Little Widget");
    }

    @Test
    public void createWidget()
            throws Exception {
        WidgetConfig         widgetConfig         = widgetConfig();
        WidgetConfigResource widgetConfigResource = widgetConfigResource();
        when(widgetService.saveWidget(any(WidgetConfig.class))).thenReturn(widgetConfig);
        MvcResult result = mvc().perform(post("/api/dashboards/1/widgets")
                                                 .contentType("application/json")
                                                 .content(toJsonWithoutLinks(widgetConfigResource)))
                .andExpect(status().isOk())
                .andExpect(containsResource(WidgetConfigResource.class))
                .andReturn();

        WidgetConfigResource resource = fromJson(result.getResponse().getContentAsString(), WidgetConfigResource.class);
        assertThat(resource.getTitle()).isEqualTo("My Little Widget");
    }

    @Test
    public void getData()
            throws Exception {
        when(widgetService.getData(eq("my_little_widget"))).thenReturn(sourceDataList());
        MvcResult result = mvc().perform(get("/api/dashboards/1/widgets/my_little_widget/data"))
                .andExpect(status().isOk())
                .andExpect(containsResource(SourceDataResource.class))
                .andReturn();

        SourceDataResource resource = fromJson(result.getResponse().getContentAsString(), SourceDataResource.class);
        assertThat(resource.getSourceData()).hasSize(3);
    }

}
