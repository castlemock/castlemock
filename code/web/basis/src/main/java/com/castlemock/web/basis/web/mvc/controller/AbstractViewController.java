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

import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.web.basis.model.ContentItem;
import com.castlemock.web.basis.model.ContentItemGroup;
import com.castlemock.web.basis.web.mvc.command.search.SearchCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
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
    private static final String PARTIAL_DIRECTORY = "partial/";
    private static final String JSP = ".jsp";
    private static final String INDEX = "index";
    private static final String PARTIAL = "partial";
    private static final String CONTENT_ITEM_GROUPS = "contentItemGroups";
    private static final String SEARCH_COMMAND = "searchCommand";

    /**
     * The method create a ModelAndView instance with the index page set as the view name
     * @return A new ModelAndView instance with index as view name
     * @see org.springframework.web.servlet.ModelAndView
     * @see #INDEX
     */
    public ModelAndView createModelAndView(){
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(APPLICATION_VERSION, version);
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
        modelAndView.addObject(CONTENT_ITEM_GROUPS, createContentItemGroups(pageContentItems));
        modelAndView.addObject(SEARCH_COMMAND, new SearchCommand());
        modelAndView.addObject(DEMO_MODE, demoMode);
        return modelAndView;
    }

    /**
     * The method is responsible for creating the various content item groups that are displayed
     * on all the pages (Except login page)
     * @return A list of content item groups
     * @see ContentItem
     * @see ContentItemGroup
     */
    private List<ContentItemGroup> createContentItemGroups(final List<ContentItem> pageContentItems){
        final List<ContentItemGroup> contentItemGroups = new ArrayList<ContentItemGroup>();
        contentItemGroups.add(createMainContentItemGroup());
        contentItemGroups.add(createPageContentItemGroup(pageContentItems));
        return contentItemGroups;
    }

    /**
     * The method is responsible for creating the main content item group. The main content group is static
     * and will remain the same on all the pages
     * @return The main content item group
     * @see ContentItem
     * @see ContentItemGroup
     */
    private ContentItemGroup createMainContentItemGroup(){
        final String mainTitle = messageSource.getMessage("general.menu.title.main", null, LocaleContextHolder.getLocale());
        final String homeTitle = messageSource.getMessage("general.menu.item.home", null, LocaleContextHolder.getLocale());
        final String userTitle = getLoggedInUsername();
        final String logTitle = messageSource.getMessage("general.menu.item.log", null, LocaleContextHolder.getLocale());
        final String configurationTitle = messageSource.getMessage("general.menu.item.configuration", null, LocaleContextHolder.getLocale());
        final String usersTitle = messageSource.getMessage("general.menu.item.users", null, LocaleContextHolder.getLocale());
        final String logoutTitle = messageSource.getMessage("general.menu.item.logout", null, LocaleContextHolder.getLocale());
        final String systemTitle = messageSource.getMessage("general.system.header.system", null, LocaleContextHolder.getLocale());

        final List<ContentItem> mainContentItems = new ArrayList<ContentItem>();
        final ContentItem homeContentItem = new ContentItem(homeTitle, getContext() + "/web", Role.READER, "fa fa-home fa-1x");
        final ContentItem userContentItem = new ContentItem(userTitle, getContext() + "/web/me", Role.READER, "fa fa-user fa-1x");
        final ContentItem logContentItem = new ContentItem(logTitle, getContext() + "/web/event", Role.READER, "fa fa-file-text fa-1x");
        final ContentItem configurationContentItem = new ContentItem(configurationTitle, getContext() + "/web/configuration", Role.ADMIN, "fa fa-cogs fa-1x");
        final ContentItem usersContentItem = new ContentItem(usersTitle, getContext() + "/web/user", Role.ADMIN, "fa fa-users fa-1x");
        final ContentItem logoutContentItem = new ContentItem(logoutTitle, getContext() + "/web/logout", Role.READER, "fa fa-sign-out fa-1x");
        final ContentItem systemContentItem = new ContentItem(systemTitle, getContext() + "/web/system", Role.ADMIN, "fa fa-desktop fa-1x");

        mainContentItems.add(homeContentItem);
        mainContentItems.add(userContentItem);
        mainContentItems.add(logContentItem);
        mainContentItems.add(configurationContentItem);
        mainContentItems.add(usersContentItem);
        mainContentItems.add(systemContentItem);
        mainContentItems.add(logoutContentItem);

        return new ContentItemGroup(mainTitle, mainContentItems);
    }

    /**
     * The method creates a content item group with all the content items for the specific page.
     * @param contentItems The content items that will be displayed on the specific page
     * @return A content item group for the specific page
     * @see ContentItem
     * @see ContentItemGroup
     */
    private ContentItemGroup createPageContentItemGroup(final List<ContentItem> contentItems){
        final String pageTitle = messageSource.getMessage("general.menu.title.page", null, LocaleContextHolder.getLocale());
        ContentItemGroup contentItemGroup = null;

        if(contentItems != null){
            contentItemGroup = new ContentItemGroup(pageTitle, contentItems);
        } else {
            contentItemGroup = new ContentItemGroup(pageTitle, new ArrayList<ContentItem>());
        }

        return contentItemGroup;
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
