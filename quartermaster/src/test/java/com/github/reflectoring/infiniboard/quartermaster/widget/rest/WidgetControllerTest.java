package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.*;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsPagedResources;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.SourceDataFactory.sourceDataList;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.WidgetConfigFactory.widgetConfig;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.WidgetConfigFactory.widgetConfigResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;
import java.util.Arrays;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.web.servlet.MvcResult;

public class WidgetControllerTest extends ControllerTestTemplate {

  @Autowired private WidgetConfigService widgetService;

  @Autowired private WidgetConfigRepository widgetConfigRepository;

  @Test
  public void getWidget() throws Exception {
    when(widgetService.loadWidget("my_little_widget")).thenReturn(widgetConfig());
    MvcResult result =
        mvc()
            .perform(get("/api/dashboards/1/widgets/my_little_widget"))
            .andExpect(status().isOk())
            .andExpect(containsResource(WidgetConfigResource.class))
            .andDo(
                document(
                    "widgets/get",
                    links(
                        halLinks(),
                        linkWithRel("self").description("Link to the widget resource itself."),
                        linkWithRel("dashboard")
                            .description("Link to the dashboard the widget is part of."),
                        linkWithRel("data")
                            .description("Link to the data the widget should display."))))
            .andReturn();

    WidgetConfigResource resource =
        fromJson(result.getResponse().getContentAsString(), WidgetConfigResource.class);
    assertThat(resource.getTitle()).isEqualTo("My Little Widget");
  }

  @Test
  public void createWidget() throws Exception {
    ConstrainedFields fields = fields(WidgetConfigResource.class);
    WidgetConfig widgetConfig = widgetConfig();
    WidgetConfigResource widgetConfigResource = widgetConfigResource();
    when(widgetService.saveWidget(any(WidgetConfig.class))).thenReturn(widgetConfig);
    MvcResult result =
        mvc()
            .perform(
                post("/api/dashboards/1/widgets")
                    .contentType("application/json")
                    .content(toJsonWithoutLinks(widgetConfigResource)))
            .andExpect(status().isOk())
            .andExpect(containsResource(WidgetConfigResource.class))
            .andDo(
                document(
                    "widgets/create",
                    requestFields(
                        fields.withPath("title").description("The display title of the widget."),
                        fields.withPath("type").description("The type of the widget."),
                        fields
                            .withPath("lastModified")
                            .description(
                                "The date the widget's configuration has last been modified."),
                        fields
                            .withPath("sourceConfigs")
                            .description(
                                "List of configurations for the data sources of this widget."))))
            .andReturn();

    WidgetConfigResource resource =
        fromJson(result.getResponse().getContentAsString(), WidgetConfigResource.class);
    assertThat(resource.getTitle()).isEqualTo("My Little Widget");
  }

  @Test
  public void getData() throws Exception {
    when(widgetService.getData(eq("my_little_widget"))).thenReturn(sourceDataList());
    MvcResult result =
        mvc()
            .perform(get("/api/dashboards/1/widgets/my_little_widget/data"))
            .andExpect(status().isOk())
            .andExpect(containsResource(SourceDataResource.class))
            .andDo(document("widgets/data/list"))
            .andReturn();

    SourceDataResource resource =
        fromJson(result.getResponse().getContentAsString(), SourceDataResource.class);
    assertThat(resource.getSourceData()).hasSize(3);
  }

  @Test
  public void getWidgets() throws Exception {
    when(widgetConfigRepository.findAll(any(Pageable.class)))
        .thenReturn(new PageImpl<>(Arrays.asList(widgetConfig())));
    MvcResult result =
        mvc()
            .perform(get("/api/dashboards/1/widgets"))
            .andExpect(status().isOk())
            .andExpect(containsPagedResources(WidgetConfigResource.class))
            .andDo(document("widgets/list"))
            .andReturn();

    PagedResources<WidgetConfigResource> resource =
        fromPagedResourceJson(
            result.getResponse().getContentAsString(), WidgetConfigResource.class);
    assertThat(resource.getContent()).hasSize(1);
  }
}
