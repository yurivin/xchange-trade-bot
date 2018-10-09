package com.github.yurivin.bot.env;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class YamlPropertiesFactory implements PropertiesFactory {

    public Properties readProperties() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Properties properties = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("application.yml").getFile());
            properties = mapper.readValue(file, Properties.class);
            log.info(properties.toString());
        } catch (Exception e) {
            log.error("Error reading properties", e);
        }
        return properties;
    }
}
