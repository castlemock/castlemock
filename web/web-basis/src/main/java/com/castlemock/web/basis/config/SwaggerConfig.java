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

package com.castlemock.web.basis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;

/**
 * The {@link SwaggerConfig} is responsible for configure Swagger.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${app.version:Undefined}")
    private String version;

    @Autowired
    private ServletContext servletContext;

    @Bean
    public Docket api() {
        final RequestParameter authorizationParameter = new RequestParameterBuilder()
                .name("Authorization")
                .in(ParameterType.HEADER)
                .required(false)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(servletContext.getContextPath() + "/api/rest/**"))
                .build()
                .apiInfo(apiInfo())
                .globalRequestParameters(List.of(authorizationParameter));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Castle Mock REST API",
                "",
                this.version,
                null,
                new Contact("Castle Mock",
                        "https://www.castlemock.com",
                        "contact@castlemock.com"),
                "Apache License 2.0",
                "https://github.com/castlemock/castlemock/blob/master/LICENSE",
                Collections.emptyList());
    }
}
