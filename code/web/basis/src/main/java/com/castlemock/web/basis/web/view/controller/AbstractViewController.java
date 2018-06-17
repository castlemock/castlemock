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

package com.castlemock.web.basis.web.view.controller;

import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.web.basis.model.ContentItem;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.view.command.search.SearchCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The AbstractViewController provides functionality that are shared amongst
 * all the view controllers
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractViewController extends AbstractController {

    @Autowired
    protected MessageSource messageSource;
    @Value("${app.version}")
    private String version;
    private static final String APPLICATION_VERSION = "appVersion";
    private static final String PAGE_CREATED = "pageCreated";
    private static final String PARTIAL_DIRECTORY = "partial/";
    private static final String JSP = ".jsp";
    private static final String INDEX = "index";
    private static final String PARTIAL = "partial";
    private static final String LOGGED_IN_USER = "loggedInUser";
    private static final String SEARCH_COMMAND = "searchCommand";
    private static final String CONTEXT = "context";
    private static final String DATE_FORMAT = "dd MMM yyyy HH:mm:ss z";
    protected static final String SELECTED_MENU = "selectedMenu";

    /**
     * The method create a ModelAndView instance with the index page set as the view name
     * @return A new ModelAndView instance with index as view name
     * @see org.springframework.web.servlet.ModelAndView
     * @see #INDEX
     */
    public ModelAndView createModelAndView(){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        final Date now = new Date();
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(APPLICATION_VERSION, version);
        modelAndView.addObject(PAGE_CREATED, simpleDateFormat.format(now));
        return modelAndView;
    }

    /**
     * The method create a ModelAndView instance with the index page set as the view name
     * @param page The partial page that will be displayed in the index page
     * @return A new ModelAndView instance with index as view name
     * @see org.springframework.web.servlet.ModelAndView
     * @see #INDEX
     */
    public ModelAndView createPartialModelAndView(final String page){
        return createPartialModelAndView(page, null);
    }

    /**
     * The method create a ModelAndView instance with the index page set as the view name
     * @param page The partial page that will be displayed in the index page
     * @param pageContentItems The content items specific for the page that will be displayed
     * @return A new ModelAndView instance with index as view name
     * @see org.springframework.web.servlet.ModelAndView
     * @see #INDEX
     */
    public ModelAndView createPartialModelAndView(final String page, final List<ContentItem> pageContentItems){
        final ModelAndView modelAndView = createModelAndView();
        modelAndView.setViewName(INDEX);
        modelAndView.addObject(PARTIAL, createPartial(page));
        modelAndView.addObject(LOGGED_IN_USER, getLoggedInUsername());
        modelAndView.addObject(SEARCH_COMMAND, new SearchCommand());
        modelAndView.addObject(DEMO_MODE, demoMode);
        modelAndView.addObject(SELECTED_MENU, MenuItem.PROJECT);
        modelAndView.addObject(CONTEXT, getContext());
        return modelAndView;
    }

    /**
     * Create a model and view that redirects the user to the start page
     * @return Model and view that redirects the user to the start page
     */
    public ModelAndView redirect(){
        return redirect("");
    }

    /**
     * Create a model and view that redirects the user to a specific url
     * @param url The url that the user should be redirected to.
     * @return A model and view that redirects the user to a specific url
     */
    public ModelAndView redirect(final String url){
        return new ModelAndView("redirect:/web" + url);
    }

    /**
     * The method gets the full partial jsp page name (PARTIAL DIRECTORY + PARTIAL NAME + .JSP).
     * @param partial The partial page name
     * @return The full partial jsp page name
     */
    protected String createPartial(final String partial){
        return PARTIAL_DIRECTORY + partial + JSP;
    }


    /**
     * Checks if a user is logged in.
     * @return True if the user is logged in. Otherwise false.
     * @see User
     */
    public boolean isLoggedIn(){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    /**
     * The method indicates which protocol is used for the incoming request: HTTP or HTTPS
     * @param request The request is used to determine the protocol
     * @return HTTP is returned if the request is not secured. HTTPS is returned if the request is secured.
     */
    protected String getProtocol(final ServletRequest request){
        return request.isSecure() ? HTTPS : HTTP;
    }

}
