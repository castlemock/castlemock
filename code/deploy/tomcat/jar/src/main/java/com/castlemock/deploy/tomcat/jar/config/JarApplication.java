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

package com.castlemock.deploy.tomcat.jar.config;

import com.castlemock.app.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;

@EnableAsync
@SpringBootApplication(scanBasePackages = "com.castlemock")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class JarApplication extends Application implements WebApplicationInitializer {

    /**
     * The main method for Castle Mock. The method is responsible for triggering the Spring application to
     * run the application.
     * @param args Provided String arguments. The arguments will be forwarded towards the SpringApplication
     * @see org.springframework.boot.SpringApplication
     */
    public static void main(final String[] args) {
        SpringApplication.run(JarApplication.class, args);
    }

    /**
     * The configure method for Castle Mock. The method is responsible for mark all the configuration classes
     * as sources for configurations for the SpringApplicationBuilder
     * @param application The Spring application builder
     * @return Returns the Spring application builder with new sources
     * @see org.springframework.boot.builder.SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(JarApplication.class, MvcConfig.class, SecurityConfig.class,
                WebSecurityConfig.class, RestSecurityConfig.class, MockSecurityConfig.class,
                PropertyConfig.class);
    }
}