package com.github.reflectoring.haljson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HalJsonResourceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getPropertyReturnsStoredPropertyValue() {
        HalJsonResource resource = new HalJsonResource();
        String          key      = "name";
        String          value    = "First";

        resource.add(key, value);
        Object object = resource.getProperty(key);

        assertThat(object, is(value));
    }

    @Test
    public void addPropertyThrowsExceptionOnExistingKey() {
        HalJsonResource resource = new HalJsonResource();
        String          key      = "name";
        String          value    = "First";

        resource.add(key, value);

        exception.expect(IllegalArgumentException.class);

        resource.add(key, value);
    }

}
