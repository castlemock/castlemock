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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The RootController is only used to redirect the user to the correct main page.
 * The reason for why this is needed is because Castle Mock has various root URLs
 * that are used for various reasons, such as SOAP and REST interaction. However,
 * when the user tries to access Castle Mock in the root context path, they should
 * be redirected to /web.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/")
public class RootController extends AbstractViewController {

    /**
     * Redirect the user to the main page for Castle Mock
     * @return A view that redirects the user to the main page for Castle Mock
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        return redirect();
    }
}
