package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.HarvesterApplication;
import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HarvesterApplication.class)
@Category(MongoIntegrationTests.class)
public class UrlDataRetrieveJobTest {

    @Autowired
    private UrlDataRetrieveJob job;

    @Autowired
    private UrlDataRepository repository;

    @Test
    public void saveToDB() {
        String url = "http://www.github.com/reflectoring/infiniboard";
        String content = "runs";
        job.updateDB(new UrlData(url, new Date(), content));

        Assert.assertTrue(repository.exists(url));
        Assert.assertEquals(content, repository.findOne(url).getContent());
    }

}