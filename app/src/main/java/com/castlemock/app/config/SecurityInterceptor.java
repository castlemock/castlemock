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

import com.auth0.jwt.interfaces.Claim;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.service.user.input.ReadUserInput;
import com.castlemock.core.basis.service.user.output.ReadUserOutput;
import com.castlemock.web.basis.config.JWTEncoderDecoder;
import com.castlemock.web.basis.service.user.UserDetailSecurityService;
import com.castlemock.web.basis.controller.AbstractController;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The Security Interceptor provides the functionality to check all the incoming request and verify that the logged
 * in users information is still valid. If not, then the user should be logged out from the system. The biggest reason
 * for why a user would suddenly be invalid is because the user's username has changed.
 * @author Karl Dahlgren
 * @since 1.0
 * @see MvcConfig
 * @see AbstractController
 *
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter implements Filter {

    @Autowired
    private ServiceProcessor serviceProcessor;
    @Autowired
    private UserDetailSecurityService userDetailSecurityService;
    @Autowired
    @Lazy
    private JWTEncoderDecoder jwtEncoderDecoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        process((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * The method will check if the logged in user is still valid.
     * @param request The incoming request.
     * @param response The outgoing response
     * @param handler The handler contains information about the method and controller that will process the incoming request
     * @return Returns true if the logged in users information is still valid. Returns false if the user is not valid
     * @throws IOException Upon unable to send a redirect as a response
     * @throws ServletException Upon unable to logout the user
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException, ServletException {
        return process(request, response);
    }

    private boolean process(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException{
        final String userId = getTokenFromCookie(request)
                .flatMap(this::getUserId)
                .orElseGet(() -> getTokenFromBearerHeader(request)
                        .flatMap(this::getUserId)
                        .orElse(null));

        if (userId == null) {
            return true;
        }

        final ReadUserInput readUserInput = ReadUserInput.builder()
                .userId(userId)
                .build();
        final ReadUserOutput readUserOutput = serviceProcessor.process(readUserInput);
        final User loggedInUser = readUserOutput.getUser();
        if(loggedInUser == null){
            LOGGER.info("The following logged in user is not valid anymore: " + userId);
            request.logout();
            response.sendRedirect(request.getContextPath());
            return false;
        } else if(!Status.ACTIVE.equals(loggedInUser.getStatus())){
            LOGGER.info("The following logged in user is not active anymore: " + userId);
            request.logout();
            response.sendRedirect(request.getContextPath());
            return false;
        } else {
            final UserDetails userDetails = new org.springframework.security.core.userdetails.User(loggedInUser.getUsername(), loggedInUser.getPassword(), ImmutableList.of(new SimpleGrantedAuthority(loggedInUser.getRole().name())));
            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, loggedInUser.getPassword(), userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return true;
        }
    }

    private Optional<String> getTokenFromCookie(final HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .map(Stream::of)
                .flatMap(cookies -> cookies
                        .filter(Objects::nonNull)
                        .filter(cookie -> cookie.getName().equals("token"))
                        .map(Cookie::getValue)
                        .findFirst());

    }

    private Optional<String> getTokenFromBearerHeader(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || authorizationHeader.length() != 2) {
            return Optional.empty();
        }

        return Optional.ofNullable(authorizationHeader.split(" ")[1]);

    }

    private Optional<String> getUserId(final String token){
        try {
            final Map<String, Claim> claims = jwtEncoderDecoder.verify(token);
            return Optional.ofNullable(claims.get("userId"))
                    .map(Claim::asString);
        } catch (final Exception exception) {
            return Optional.empty();
        }

    }

}
