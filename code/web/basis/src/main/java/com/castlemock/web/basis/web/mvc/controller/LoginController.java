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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * The LoginController provides the functionality to login to Castle Mock.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/login")
public class LoginController extends AbstractViewController {

    @Autowired
    private MessageSource messageSource;

    private static final String PAGE = "login";
    private static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";
    private static final String MSG = "msg";
    private static final String ERROR = "error";
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    /**
     * The login method examines the status of the current user and determines which action should be taken.
     * The following outcomes are possible:
     * 1. The user is already logged in, then the user should be redirected to the main page.
     * 2. The user is not logged in and will try to login. The user will get to the login
     * view page. If the user have logged out and been redirected to the login page, the a message
     * should be displayed informing the user that they have been successfully logged out. Another
     * message could be displayed if the user provided invalid login credentials or if the login
     * didn't work.
     * to the page
     * @param error The error message if a logged in failed due to various reasons
     * @param logout The logout variable is use to determined if user has been logged out and redirected to the login page.
     * @param request The login request, which contains the specific error event
     * @return The login view (With special messages in case of any specific event has occurred).
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) final String error, @RequestParam(value = "logout", required = false) final String logout, final HttpServletRequest request) {

        if(isLoggedIn()){
            LOGGER.debug("User is already logged in. Redirecting the user.");
            return redirect();
        }

        final ModelAndView model = createModelAndView();
        model.setViewName(PAGE);
        model.addObject(DEMO_MODE, demoMode);
        if (error != null) {
            LOGGER.debug("Unable to login");
            model.addObject(ERROR, getErrorMessage(request, SPRING_SECURITY_LAST_EXCEPTION));
        }
        if (logout != null) {
            LOGGER.debug("Logout successful");
            model.addObject(MSG, messageSource.getMessage("general.login.label.logoutsuccessful", null , LocaleContextHolder.getLocale()));
        }

        return model;
    }

    /**
     * The method is used to extract the error message from the request
     * @param request The request that contains the exception and error message
     * @param key The key is used to find the exception attribute in the request.
     * @return Returns the error message that will be displayed to the user on the login page.
     */
    private String getErrorMessage(final HttpServletRequest request, final String key) {

        final Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException || exception.getCause() instanceof NullPointerException || exception.getCause() instanceof IllegalArgumentException) {
            LOGGER.debug("Invalid username or password");
            error = messageSource.getMessage("general.login.label.invalidcredentials", null, LocaleContextHolder.getLocale());
        } else if (exception instanceof LockedException) {
            LOGGER.debug("User has been locked");
            error = messageSource.getMessage("general.login.label.userlocked", null ,LocaleContextHolder.getLocale());
        } else if(exception instanceof CredentialsExpiredException){
            LOGGER.debug("User has been inactive");
            error = messageSource.getMessage("general.login.label.userinactive", null ,LocaleContextHolder.getLocale());
        } else {
            LOGGER.error("Unable to login due to unknown reasons");
            LOGGER.error(exception.getMessage(), exception);
            error = messageSource.getMessage("general.login.label.unknownreason", null ,LocaleContextHolder.getLocale());
        }

        return error;
    }

}