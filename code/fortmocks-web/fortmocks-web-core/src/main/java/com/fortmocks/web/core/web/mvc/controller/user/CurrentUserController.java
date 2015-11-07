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

package com.fortmocks.web.core.web.mvc.controller.user;

import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.service.UserService;
import com.fortmocks.web.core.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The UserController provides functionality to retrieve user information.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/me")
public class CurrentUserController extends AbstractViewController {

    private static final String PAGE = "core/user/currentUser";

    @Autowired
    private UserService userService;

    /**
     * The service provides the functionality to retrieve information about the user that is currently logged in.
     * The user will be able to both view their information, but also update their information through the page.
     * @return A view that displays information about the user being currently logged in
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final String loggedInUsername = getLoggedInUsername();
        final UserDto userDto = userService.findByUsername(loggedInUsername);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(USER, userDto);
        return model;
    }

}