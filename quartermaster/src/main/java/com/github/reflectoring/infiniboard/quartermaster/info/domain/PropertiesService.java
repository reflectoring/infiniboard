package com.github.reflectoring.infiniboard.quartermaster.info.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

    public Properties loadProperties(String propertiesFile) throws IOException {

        Properties props = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFile);

        if (is == null) {
            throw new FileNotFoundException(propertiesFile + " not found in classpath!");
        }

        props.load(is);
        return props;
    }
}
