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

package com.fortmocks.war.config;

import com.fortmocks.web.basis.model.session.token.repository.SessionTokenRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The class SecurityConfig provides the configuration for the entire Fort Mocks security
 *
 * @author Karl Dahlgren
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("tokenRepository")
    private SessionTokenRepository tokenRepository;
    @Value(value = "${token.validity.seconds}")
    private Integer tokenValiditySeconds;
    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);

    /**
     * Configure which attributes will be used for when doing authentication
     *
     * @param authenticationManagerBuilder The authentication manager builder
     * @throws Exception Throws an exception if the configuration fails
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        try {
            authenticationManagerBuilder.inMemoryAuthentication().withUser("user").password("password").authorities("ROLE_USER");
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        } catch (Exception exception) {
            LOGGER.error("Unable to configure the authentication manager builder", exception);
            throw new IllegalStateException("Unable to configure the authentication manager builder");
        }
    }

    /**
     * The method configure is responsible for the security configuration.
     *
     * @param httpSecurity httpSecurity will be used to configure the authentication process.
     * @throws Exception Throws an exception if the configuration fails
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/web/**")
                .access("hasRole('READER') or hasRole('MODIFIER') or hasRole('ADMIN')").and().formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutSuccessUrl("/login?logout")
                .and().csrf().and().rememberMe().tokenRepository(tokenRepository).tokenValiditySeconds(tokenValiditySeconds)
                .and().exceptionHandling().accessDeniedPage("/forbidden");
        httpSecurity.authorizeRequests().antMatchers("/mock/**").permitAll().and().csrf().disable();
    }

    /**
     * Create the password encoder
     *
     * @return A new BCrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}