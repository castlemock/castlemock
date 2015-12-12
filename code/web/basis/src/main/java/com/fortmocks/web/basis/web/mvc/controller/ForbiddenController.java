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

package com.fortmocks.web.basis.web.mvc.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The ForbiddenController is used when a user tries to access
 * a section which the user does not have access to.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/forbidden")
public class ForbiddenController extends AbstractViewController {

    private static final String PAGE = "forbidden.jsp";

    /**
     * The method is called every time when a user tries to access a section which the user
     * is not allowed to access.
     * The method redirects the user to the login page if the user is not logged in. If the
     * user is logged in, then the user will be redirected to a specific forbidden page.
     * @return A view that displays a message that the requested content is not accessible for the user.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView forbiddenGet() {
        if(!isLoggedIn()){
            return redirect();
        }
        final ModelAndView model = createModelAndView();
        model.setViewName(PAGE);
        return model;
    }

}