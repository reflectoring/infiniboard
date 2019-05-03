package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.fromJson;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.fromPagedResourceJson;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsPagedResources;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.DashboardFactory.dashboard;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.DashboardFactory.dashboardList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.DashboardService;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.web.servlet.MvcResult;

public class DashboardControllerTest extends ControllerTestTemplate {

  @Autowired private DashboardService dashboardService;

  @Test
  public void getAllDashboards() throws Exception {
    when(dashboardService.loadAll(any(Pageable.class)))
        .thenReturn(new PageImpl<>(dashboardList(), new PageRequest(0, 1), 1));

    MvcResult result =
        mvc()
            .perform(get("/api/dashboards"))
            .andExpect(status().isOk())
            .andExpect(containsPagedResources(DashboardResource.class))
            .andDo(document("dashboards/list"))
            .andReturn();

    PagedResources<DashboardResource> pagedResources =
        fromPagedResourceJson(result.getResponse().getContentAsString(), DashboardResource.class);
    assertThat(pagedResources.getContent()).hasSize(1);
  }

  @Test
  public void getDashboard() throws Exception {
    when(dashboardService.load("1")).thenReturn(dashboard());

    MvcResult result =
        mvc()
            .perform(get("/api/dashboards/1"))
            .andExpect(status().isOk())
            .andExpect(containsResource(DashboardResource.class))
            .andDo(
                document(
                    "dashboards/get",
                    links(
                        halLinks(),
                        linkWithRel("self").description("Link to the dashboard itself."),
                        linkWithRel("widgets")
                            .description(
                                "Link to the paged collection of widgets configured for this dashboard."),
                        linkWithRel("all-widgets")
                            .description(
                                "Link to the unpaged collection of widgets configured for this dashboard."))))
            .andReturn();

    DashboardResource resource =
        fromJson(result.getResponse().getContentAsString(), DashboardResource.class);
    assertThat(resource).isNotNull();
  }
}
