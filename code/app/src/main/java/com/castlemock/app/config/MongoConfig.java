package com.castlemock.app.config;

import com.castlemock.repository.Profiles;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

public class MongoConfig {

    @Configuration
    @Profile(Profiles.MONGODB)
    @EnableConfigurationProperties(MongoProperties.class)
    public static class MongoAutoConfiguration
            extends org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration {

        public MongoAutoConfiguration(MongoProperties properties,
                                      ObjectProvider<MongoClientOptions> options, Environment environment) {
            super(properties, options, environment);
        }
    }

    @Configuration
    @Profile(Profiles.MONGODB)
    @AutoConfigureAfter(MongoAutoConfiguration.class)
    @EnableConfigurationProperties(MongoProperties.class)
    public static class MongoDataAutoConfiguration
            extends org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration {

        public MongoDataAutoConfiguration(ApplicationContext applicationContext, MongoProperties properties) {
            super(applicationContext, properties);
        }
    }

}
