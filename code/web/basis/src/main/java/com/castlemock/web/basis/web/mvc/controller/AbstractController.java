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

package com.castlemock.web.basis.web.mvc.controller;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletContext;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * The AbstractController provides functionality that are shared among all the controllers in Castle Mock
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractController {

    @Value("${server.mode.demo}")
    protected boolean demoMode;
    @Value("${server.endpoint.address:}")
    protected String endpointAddress;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    protected ServiceProcessor serviceProcessor;

    protected static final String DEMO_MODE = "demoMode";
    protected static final String CONTENT_TYPE = "Content-Type";
    protected static final String COMMAND = "command";
    protected static final String DIVIDER = ":";
    protected static final String EMPTY = "";
    protected static final String EVENTS = "events";
    protected static final String EVENT = "event";
    protected static final String MOCK = "mock";
    protected static final String NEW_LINE = System.lineSeparator();
    protected static final String PROJECT = "project";
    protected static final String PROJECTS = "projects";
    protected static final String PROJECT_TYPES = "projectTypes";
    protected static final String ROLES = "roles";
    protected static final String SLASH = "/";
    protected static final String SPACE = " ";
    protected static final String USER = "user";
    protected static final String USERS = "users";
    protected static final String USER_STATUSES = "userStatuses";
    protected static final String FILE_UPLOAD_FORM = "uploadForm";
    protected static final String HTTP = "http://";
    protected static final String HTTPS = "https://";
    private static final String LOCAL_ADDRESS = "127.0.0.1";

    private static final String ANONYMOUS_USER = "Anonymous";
    /**
     * Returns the current application context for Castle Mock. For example the /castlemock in http://localhost:8080/castlemock
     * @return The current application context
     */
    protected String getContext(){
        return servletContext.getContextPath();
    }

    /**
     * Get the current logged in user username
     * @return The username of the current logged in user
     * @see User
     */
    protected String getLoggedInUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ANONYMOUS_USER; // Should never happened except during unit tests
        }
        return authentication.getName();
    }

    /**
     * The method returns the local address (Not link local address, not loopback address) which the server is deployed on.
     * If the method does not find any INet4Address that is neither link local address and loopback address, the method
     * will return the the address 127.0.0.1
     * @return Returns the local address or 127.0.0.1 if no address was found
     * @throws SocketException Upon failing to extract network interfaces
     */
    public String getHostAddress() throws SocketException {
        if(endpointAddress != null && !endpointAddress.isEmpty()){
            return endpointAddress;
        }

        final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements())
        {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            final Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                final InetAddress address = addresses.nextElement();
                if (!address.isLinkLocalAddress() && !address.isLoopbackAddress() && address instanceof Inet4Address){
                    return address.getHostAddress();
                }
            }
        }

        return LOCAL_ADDRESS;
    }

}
