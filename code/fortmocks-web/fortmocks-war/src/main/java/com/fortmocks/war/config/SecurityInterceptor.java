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

import com.fortmocks.core.model.user.domain.Role;
import com.fortmocks.core.model.user.domain.Status;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.ReadUserByUsernameInput;
import com.fortmocks.core.model.user.message.ReadUserByUsernameOutput;
import com.fortmocks.web.core.model.user.service.UserDetailSecurityService;
import com.fortmocks.web.core.processor.ProcessorMainframe;
import com.fortmocks.web.core.web.mvc.controller.AbstractController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ProcessorMainframe processorMainframe;
    @Autowired
    private UserDetailSecurityService userDetailSecurityService;
    private static final Logger LOGGER = Logger.getLogger(SecurityInterceptor.class);

    /**
     * The method will check if the logged in user is still valid.
     * @param request The incoming request.
     * @param response The outgoing response
     * @param handler The handler contains information about the method and controller that will process the incoming request
     * @return Returns true if the logged in users information is still valid. Returns false if the user is not valid
     * @throws Exception
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return true;
        }

        final String loggedInUsername = authentication.getName();
        if("anonymousUser".equals(loggedInUsername)) {
            return true;
        }

        final ReadUserByUsernameInput readUserByUsernameInput = new ReadUserByUsernameInput();
        readUserByUsernameInput.setUsername(loggedInUsername);
        final ReadUserByUsernameOutput readUserByUsernameOutput = processorMainframe.process(readUserByUsernameInput);
        final UserDto loggedInUser = readUserByUsernameOutput.getUser();
        if(loggedInUser == null){
            LOGGER.info("The following logged in user is not valid anymore: " + loggedInUsername);
            request.logout();
            response.sendRedirect(request.getContextPath());
            return false;
        } else if(!Status.ACTIVE.equals(loggedInUser.getStatus())){
            LOGGER.info("The following logged in user is not active anymore: " + loggedInUsername);
            request.logout();
            response.sendRedirect(request.getContextPath());
            return false;
        } else {
            for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
                Role role = Role.valueOf(grantedAuthority.getAuthority());
                if(!loggedInUser.getRole().equals(role)){
                    LOGGER.info("The following logged in user's authorities has been updated: " + loggedInUsername);
                    final UserDetails userDetails = userDetailSecurityService.loadUserByUsername(loggedInUsername);
                    final Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                }
            }
            return true;
        }
    }

}
