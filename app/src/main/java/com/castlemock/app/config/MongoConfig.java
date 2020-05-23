/*
 * Copyright 2018 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.app.config;

import com.castlemock.repository.Profiles;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Conditionally enable mongodb configurations.
 *
 * @author Mohammad Hewedy
 * @since 1.35
 */
public class MongoConfig {

    @Configuration
    @Profile(Profiles.MONGODB)
    @EnableConfigurationProperties(MongoProperties.class)
    public static class MongoAutoConfiguration
            extends org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration {

    }

    @Configuration
    @Profile(Profiles.MONGODB)
    @AutoConfigureAfter(MongoAutoConfiguration.class)
    @EnableConfigurationProperties(MongoProperties.class)
    public static class MongoDataAutoConfiguration
            extends org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration {

        public MongoDataAutoConfiguration(MongoProperties properties) {
            super();
        }
    }

}
