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

package com.castlemock.web.mock.soap.config;

import com.castlemock.web.core.filter.HttpServletRequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class specifically for the SOAP component.
 * @author Karl Dahlgren
 * @since 1.18
 */
@Configuration
public class SoapConfig {

    /**
     * Register a {@link HttpServletRequestWrapperFilter}
     */
    @Bean
    public FilterRegistrationBean<HttpServletRequestWrapperFilter> soapRequestWrapperFilter() {
        final FilterRegistrationBean<HttpServletRequestWrapperFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        registration.addUrlPatterns("/mock/soap/project/*");
        registration.setName("soapRequestWrapperFilter");
        registration.setOrder(1);
        return registration;
    }

}
