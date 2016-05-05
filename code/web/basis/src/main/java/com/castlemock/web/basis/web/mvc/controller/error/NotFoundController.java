/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.web.basis.web.mvc.controller.error;

import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Karl Dahlgren
 * @since 1.2
 */
@Controller
public class NotFoundController extends AbstractViewController {

    private static final String USER_LOGGED_IN_PAGE = "basis/error/notFound";
    private static final String USER_NOT_LOGGED_IN_PAGE = "error/notFound";

    @RequestMapping(value = "/web/error/404", method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        ModelAndView model;
        if(isLoggedIn()){
            model  = createPartialModelAndView(USER_LOGGED_IN_PAGE);
        } else {
            model = createModelAndView();
            model.setViewName(USER_NOT_LOGGED_IN_PAGE);
        }

        return model;
    }

}
