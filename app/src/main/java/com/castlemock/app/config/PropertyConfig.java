package com.castlemock.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:file-repository.properties",
        "classpath:spring.properties",
        "classpath:swagger.properties",
        "classpath:version.properties",
})
public class PropertyConfig {
}
