/*
 * Copyright 2018 Karl Dahlgren
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@link ApiDocumentationController} will redirect the user to the Swagger resources.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@Scope("request")
@RequestMapping("/doc")
public class ApiDocumentationController extends AbstractViewController {


    @RequestMapping(value = "api/rest", method = RequestMethod.GET)
    public ModelAndView restApi() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

}