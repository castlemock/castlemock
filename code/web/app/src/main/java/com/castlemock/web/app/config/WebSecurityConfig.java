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

package com.castlemock.web.app.config;

import com.castlemock.web.basis.repository.token.SessionTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class {@link WebSecurityConfig} provides the configuration for the web interface.
 *
 * @author Karl Dahlgren
 * @since 1.25
 */
@Configuration
@Order(3)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("tokenRepository")
    private SessionTokenRepository tokenRepository;
    @Value(value = "${token.validity.seconds}")
    private Integer tokenValiditySeconds;

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
                    .antMatchers("/web/**")
                    .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").failureUrl("/login?error")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .logoutSuccessUrl("/login?logout")
                    .and()
                .csrf().and().rememberMe().tokenRepository(tokenRepository).tokenValiditySeconds(tokenValiditySeconds)
                .and().exceptionHandling().accessDeniedPage("/forbidden");

        httpSecurity.headers().cacheControl().disable();
    }

}