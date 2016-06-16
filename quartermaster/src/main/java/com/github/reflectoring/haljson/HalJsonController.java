package com.github.reflectoring.haljson;

import java.time.LocalDate;

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
        resource.add("modified", LocalDate.now());
        resource.add(new Link("self", "http://localhost:8090/api/test"));

        HalJsonResource transcript1Resource = getTranscriptOne();
        HalJsonResource transcript2Resource = getTranscriptTwo();

        resource.add("transcript-of-records", transcript1Resource);
        resource.add("transcript-of-records", transcript2Resource);

        return new ResponseEntity<>(resource, OK);

    }

    private HalJsonResource getTranscriptOne() {
        HalJsonResource transcriptResource = new HalJsonResource();
        transcriptResource.add(new Link("self", "http://localhost:8090/api/test/1"));
        transcriptResource.add(new Link("second", "http://localhost:8090/api/other/1"));
        transcriptResource.add("math", "A");
        transcriptResource.add("english", "B");
        return transcriptResource;
    }

    private HalJsonResource getTranscriptTwo() {
        HalJsonResource transcriptResource = new HalJsonResource();
        transcriptResource.add(new Link("self", "http://localhost:8090/api/test/1"));
        transcriptResource.add(new Link("second", "http://localhost:8090/api/other/2"));
        transcriptResource.add("latin", "A");
        transcriptResource.add("german", "A");
        return transcriptResource;
    }
}
