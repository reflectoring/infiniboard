package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.*;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsPagedResources;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.factory.DashboardFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.reflectoring.infiniboard.packrat.dashboard.Dashboard;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.DashboardService;
import com.github.reflectoring.infiniboard.quartermaster.exception.ErrorResource;
import com.github.reflectoring.infiniboard.quartermaster.exception.ResourceAlreadyExistsException;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.restdocs.hypermedia.Link;
import org.springframework.test.web.servlet.MvcResult;

public class DashboardControllerTest extends ControllerTestTemplate {

  @Autowired private DashboardService dashboardService;

  @Autowired private DashboardResourceAssembler dashboardResourceAssembler;

  @Before
  public void resetMocks() {
    reset(dashboardService);
  }

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

  @Test
  public void createDashboard() throws Exception {
    ConstrainedFields fields = fields(DashboardResource.class);

    DashboardResource dashboardResource = new DashboardResource();
    dashboardResource.setName("Development Dashboard");
    dashboardResource.setSlug("development");
    dashboardResource.setDescription("Project metrics aggregated for development teams.");

    Dashboard dashboard = dashboardResourceAssembler.toEntity(dashboardResource);
    dashboard.setId("5ccd9d3b410df800012301ac");

    when(dashboardService.save(any())).thenReturn(dashboard);

    MvcResult result =
        mvc()
            .perform(
                post("/api/dashboards")
                    .contentType("application/json")
                    .content(toJsonWithoutLinks(dashboardResource, IgnoreNumberMixin.class)))
            .andExpect(status().isOk())
            .andExpect(containsResource(DashboardResource.class))
            .andDo(
                document(
                    "dashboards/create",
                    requestFields(
                        fields.withPath("name").description("The display title of the dashboard."),
                        fields.withPath("slug").description("The url slug of the dashboard."),
                        fields
                            .withPath("description")
                            .description("The description of the dashboard."))))
            .andReturn();

    DashboardResource resource =
        fromJson(result.getResponse().getContentAsString(), DashboardResource.class);
    assertThat(resource).isNotNull();
    assertThat(resource).hasFieldOrProperty("number");
    assertThat(resource).hasFieldOrProperty("name");
    assertThat(resource).hasFieldOrProperty("slug");
    assertThat(resource).hasFieldOrProperty("description");
  }

  @Test
  public void createDashboardWithInvalidSlug() throws Exception {

    DashboardResource dashboardWithBadName = new DashboardResource();
    dashboardWithBadName.setName("Test Dashboard");
    dashboardWithBadName.setSlug("test dashboard");
    dashboardWithBadName.setDescription("Dashboard with invalid slug containing spaces");
    MvcResult result =
        mvc()
            .perform(
                post("/api/dashboards")
                    .contentType("application/json")
                    .content(toJsonWithoutLinks(dashboardWithBadName)))
            .andExpect(status().isBadRequest())
            .andExpect(containsResource(ErrorResource.class))
            .andReturn();

    ErrorResource resource =
        fromJson(result.getResponse().getContentAsString(), ErrorResource.class);
    assertThat(resource).hasFieldOrProperty("errors");
    assertThat(resource).extracting("errors").hasSize(1);
  }

  @Test
  public void createDashboardWithMissingSlug() throws Exception {

    DashboardResource dashboardWithBadName = new DashboardResource();
    dashboardWithBadName.setName("Test Dashboard");
    dashboardWithBadName.setDescription("Dashboard with missing slug");
    MvcResult result =
        mvc()
            .perform(
                post("/api/dashboards")
                    .contentType("application/json")
                    .content(toJsonWithoutLinks(dashboardWithBadName)))
            .andExpect(status().isBadRequest())
            .andExpect(containsResource(ErrorResource.class))
            .andReturn();

    ErrorResource resource =
        fromJson(result.getResponse().getContentAsString(), ErrorResource.class);
    assertThat(resource).hasFieldOrProperty("errors");
    assertThat(resource).extracting("errors").hasSize(1);
  }

  @Test
  public void createDashboardWithExistingSlug() throws Exception {

    when(dashboardService.save(any())).thenThrow(new ResourceAlreadyExistsException());

    DashboardResource dashboardWithBadName = new DashboardResource();
    dashboardWithBadName.setName("Test Dashboard");
    dashboardWithBadName.setSlug("existing-slug");
    dashboardWithBadName.setDescription("Dashboard");
    MvcResult result =
        mvc()
            .perform(
                post("/api/dashboards")
                    .contentType("application/json")
                    .content(toJsonWithoutLinks(dashboardWithBadName)))
            .andExpect(status().isBadRequest())
            .andExpect(containsResource(ErrorResource.class))
            .andReturn();

    ErrorResource resource =
        fromJson(result.getResponse().getContentAsString(), ErrorResource.class);
    assertThat(resource).hasFieldOrProperty("errors");
    assertThat(resource).extracting("errors").hasSize(1);
  }

  public abstract class IgnoreNumberMixin {
    @JsonIgnore
    abstract List<Link> getLinks();

    @JsonIgnore String number;
  }
}
