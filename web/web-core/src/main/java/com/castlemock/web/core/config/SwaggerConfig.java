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

package com.castlemock.web.core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.servlet.ServletContext;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@link SwaggerConfig} is responsible for configure Swagger.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Configuration
public class SwaggerConfig {

    @Value("${app.version:Undefined}")
    private String version;

    @Autowired
    private ServletContext servletContext;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Castle Mock")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Castle Mock")
                        .description("Castle Mock is a web application that provides the functionality to mock out RESTful APIs and SOAP web-services. " +
                                "This functionality allows client-side developers to completely mimic a server side behavior and shape the responses " +
                                "themselves for when writing and conducting integration tests.")
                        .version("v1")
                        .license(new License().name("Apache License 2.0").url("https://github.com/castlemock/castlemock/blob/master/LICENSE")))
                .externalDocs(new ExternalDocumentation()
                        .description("Castle Mock Wiki Documentation")
                        .url("https://github.com/castlemock/castlemock/wiki"));
    }
}
