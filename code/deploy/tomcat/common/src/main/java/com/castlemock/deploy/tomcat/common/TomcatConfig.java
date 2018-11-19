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

package com.castlemock.deploy.tomcat.common;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class TomcatConfig {

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        final Set<ErrorPage> errorPages = new HashSet<>();
        errorPages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/web/error/404"));
        factory.setErrorPages(errorPages);

        final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("xsd", "text/xml; charset=utf-8");
        mappings.add("ico", "image/x-icon");
        factory.setMimeMappings(mappings);

        return factory;
    }

}
