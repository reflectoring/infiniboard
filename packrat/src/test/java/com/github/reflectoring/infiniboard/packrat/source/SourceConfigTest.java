package com.github.reflectoring.infiniboard.packrat.source;

import com.github.reflectoring.infiniboard.packrat.PackratTestApplication;
import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * testing the source configurations
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PackratTestApplication.class)
@Category(MongoIntegrationTests.class)
public class SourceConfigTest {

    @Autowired
    private SourceConfigRepository repository;

    @Before
    public void setup() {

    }

    @Test
    public void configWasSaved() {
    }

    @Test
    public void variableConfig() {
    }

}