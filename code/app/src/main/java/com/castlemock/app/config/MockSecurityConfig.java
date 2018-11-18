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

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class {@link MockSecurityConfig} provides the configuration for security regarding the mock services.
 *
 * @author Karl Dahlgren
 * @since 1.25
 */
@Configuration
@Order(1)
public class MockSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The method configure is responsible for the security configuration.
     *
     * @param httpSecurity httpSecurity will be used to configure the authentication process.
     * @throws Exception Throws an exception if the configuration fails
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .antMatcher("/mock/**")
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                .csrf()
                    .disable();
    }

}