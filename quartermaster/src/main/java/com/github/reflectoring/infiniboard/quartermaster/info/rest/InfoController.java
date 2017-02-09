package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.github.reflectoring.infiniboard.quartermaster.info.domain.PropertiesService;
import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class InfoController {

  private PropertiesService propertiesService;

  @Autowired
  public InfoController(PropertiesService propertiesService) {
    this.propertiesService = propertiesService;
  }

  @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InfoResource> getAllDashboards() {
    return new ResponseEntity<>(getInfoResource(), OK);
  }

  private InfoResource getInfoResource() {
    InfoResource resource = new InfoResource();

    try {
      Properties properties = propertiesService.loadProperties("quartermaster.properties");
      String version = properties.getProperty("version");
      resource.setVersion(version);
    } catch (IOException e) {
      System.err.println("error retrieving properties: " + e.getMessage());
      resource.setVersion("N/A");
    }

    return resource;
  }
}
