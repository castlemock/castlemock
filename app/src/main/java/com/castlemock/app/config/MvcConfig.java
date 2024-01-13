/*
 * Copyright 2015 Karl Dahlgren
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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The MvcConfig class is responsible for configuring the MVC related configurations
 * @author Karl Dahlgren
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = { "com.castlemock" })
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                .resourceChain(false)
                .addResolver(new PushStateResourceResolver());

        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName("forward:/index.html");
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


    /**
     * Creates a new {@link LoggingInterceptor}
     * @return A new {@link LoggingInterceptor} instance
     */
    @Bean
    public LoggingInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

    /**
     * Configure the content negotiation. This method will set the
     * {@link ContentNegotiationConfigurer#favorPathExtension(boolean)} to true
     * which will allow REST request mappings to include dots.
     * @param configurer The content negotiation configurer
     * @since 1.12
     */
    @Override
    @SuppressWarnings("deprecation")
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
