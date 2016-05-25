package com.github.reflectoring.haljson;

import com.github.reflectoring.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/test")
public class HalJsonController {

    @RequestMapping(method = GET)
    public ResponseEntity<HalJsonResource> getTestResource() {
        HalJsonResource resource = new HalJsonResource();
        resource.add("name", "Hans Müller");
        resource.add("age", 30);
        resource.add(new Link("self", "http://localhost:8090/api/test"));

        HalJsonResource transcriptResource = new HalJsonResource();
        transcriptResource.add(new Link("self", "http://localhost:8090/api/test/1"));
        transcriptResource.add("math", "A");
        transcriptResource.add("english", "B");
        resource.add("transcript-of-records", transcriptResource);

        return new ResponseEntity<>(resource, OK);

    }
}
