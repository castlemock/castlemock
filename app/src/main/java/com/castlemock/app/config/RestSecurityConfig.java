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

import com.castlemock.web.core.config.JWTEncoderDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The class {@link RestSecurityConfig} provides the configuration for the REST interfaces.
 *
 * @author Karl Dahlgren
 * @since 1.25
 */
@Configuration
@Order(2)
@EnableWebSecurity
public class RestSecurityConfig {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityInterceptor securityInterceptor;


    @Bean
    public SecurityFilterChain restSecurityFilterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz.requestMatchers("/api/rest/core/login", "/api/rest/core/version", "/api/rest/core/context", "/doc/api/rest", "/mock/**")
                        .permitAll()
                ).authorizeHttpRequests((authz) -> authz.requestMatchers("/api/rest/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll()
                )
                .httpBasic((authz) -> authz.authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint()))
                .csrf(AbstractHttpConfigurer::disable)
                .headers((config) -> config.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                .addFilterBefore(securityInterceptor, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public JWTEncoderDecoder jwtEncoderDecoder() {
        return new JWTEncoderDecoder();
    }

}