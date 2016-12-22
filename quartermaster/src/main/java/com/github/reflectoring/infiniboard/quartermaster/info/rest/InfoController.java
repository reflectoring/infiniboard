package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
