package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import org.junit.Test;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.web.servlet.MvcResult;

import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.fromJson;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.fromPagedResourceJson;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsPagedResources;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DashboardControllerTest extends ControllerTestTemplate {

    @Test
    public void getAllDashboards()
            throws Exception {
        MvcResult result = mvc().perform(get("/api/dashboards"))
                .andExpect(status().isOk())
                .andExpect(containsPagedResources(DashboardResource.class))
                .andDo(document("dashboards/list"))
                .andReturn();

        PagedResources<DashboardResource> pagedResources =
                fromPagedResourceJson(result.getResponse().getContentAsString(), DashboardResource.class);
        assertThat(pagedResources.getContent()).hasSize(1);
    }

    @Test
    public void getDashboard()
            throws Exception {
        MvcResult result = mvc().perform(get("/api/dashboards/1"))
                .andExpect(status().isOk())
                .andExpect(containsResource(DashboardResource.class))
                .andDo(document("dashboards/get",
                                links(halLinks(),
                                      linkWithRel("self").description("Link to the dashboard itself."),
                                      linkWithRel("widgets").description(
                                              "Link to the collection of widgets configured for this dashboard."))))
                .andReturn();

        DashboardResource resource = fromJson(result.getResponse().getContentAsString(), DashboardResource.class);
        assertThat(resource).isNotNull();
    }

}
