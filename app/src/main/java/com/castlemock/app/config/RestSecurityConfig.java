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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@SuppressWarnings("deprecation")
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityInterceptor securityInterceptor;

    /**
     * The method configure is responsible for the security configuration.
     *
     * @param httpSecurity httpSecurity will be used to configure the authentication process.
     * @throws Exception Throws an exception if the configuration fails
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/api/rest/core/login", "/api/rest/core/version", "/api/rest/core/context", "/doc/api/rest")
                    .permitAll()
                .and()
                .antMatcher("/api/rest/**")
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                .and()
                    .httpBasic()
                    .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                .and()
                    .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.headers().cacheControl().disable();
        httpSecurity.addFilterBefore(securityInterceptor, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public JWTEncoderDecoder jwtEncoderDecoder() {
        return new JWTEncoderDecoder();
    }

    @Autowired
    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

}