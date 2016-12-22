package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum QuartermasterInfos {
    VERSION("version");

    public static final String PROPERTIES_FILE = "quartermaster.properties";

    private String     key;
    private Properties properties;

    QuartermasterInfos(String key) {
        this.key = key;
        try {
            this.properties = loadProperties();
        } catch (IOException e) {
            System.err.println("error retrieving properties: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        if (properties.containsKey(this.key)) {
            return properties.get(this.key).toString();
        } else {
            throw new RuntimeException("Key '" + this.key + "' is not defined in '" + PROPERTIES_FILE + "'");
        }
    }

    private Properties loadProperties()
            throws IOException {

        Properties  props = new Properties();
        InputStream is    = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);

        if (is == null) {
            throw new FileNotFoundException(PROPERTIES_FILE + " not found in classpath!");
        }

        props.load(is);
        return props;
    }

}
