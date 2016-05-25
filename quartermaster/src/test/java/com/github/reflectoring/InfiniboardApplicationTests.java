package com.github.reflectoring;

import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Quartermaster.class)
@WebAppConfiguration
@Category(MongoIntegrationTests.class)
public class InfiniboardApplicationTests {

    @Test
    public void contextLoads() {
    }

}
