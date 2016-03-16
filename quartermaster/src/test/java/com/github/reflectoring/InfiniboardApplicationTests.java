package com.github.reflectoring;

import com.github.reflectoring.infiniboard.test.categories.MongoIntegrationTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InfiniboardApplication.class)
@WebAppConfiguration
@Category(MongoIntegrationTests.class)
public class InfiniboardApplicationTests {

	@Test
	public void contextLoads() {
	}

}
