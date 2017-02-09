package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import static com.github.reflectoring.infiniboard.quartermaster.testframework.JsonHelper.fromJson;
import static com.github.reflectoring.infiniboard.quartermaster.testframework.ResultMatchers.containsResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

public class InfoControllerTest extends ControllerTestTemplate {

  @Test
  public void getQuartermasterInfo() throws Exception {

    MvcResult result =
        mvc()
            .perform(get("/api/info"))
            .andExpect(status().isOk())
            .andExpect(containsResource(InfoResource.class))
            .andDo(document("api/info"))
            .andReturn();

    InfoResource infoResource =
        fromJson(result.getResponse().getContentAsString(), InfoResource.class);
    assertThat(infoResource.getVersion()).isNotEmpty();
  }
}
