package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.Dashboard;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.rest.DashboardResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/info")
public class InfoController {

    @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InfoResource> getAllDashboards() {
        return new ResponseEntity<>(getInfoResource(), OK);
    }

    private InfoResource getInfoResource() {
        InfoResource resource = new InfoResource();
        resource.setVersion(QuartermasterInfos.VERSION.toString());
        return resource;
    }

}
