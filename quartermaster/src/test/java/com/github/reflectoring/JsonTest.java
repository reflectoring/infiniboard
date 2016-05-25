package com.github.reflectoring;

import com.github.reflectoring.haljson.Json;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getPropertyReturnsStoredPropertyValue() {
        Json resource = new Json();
        String key = "name";
        String value = "First";

        resource.add(key, value);
        Object object = resource.getProperty(key);

        assertThat(object, is(value));
    }

    @Test
    public void addPropertyThrowsExceptionOnExistingKey() {
        Json resource = new Json();
        String key = "name";
        String value = "First";

        resource.add(key, value);

        exception.expect(IllegalArgumentException.class);

        resource.add(key, value);
    }

}
