/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.basis.web.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@Controller
@RequestMapping("/doc/api/rest")
@Api(value="Core", tags = {"Core"})
public class DocumentationController {

    /**
     * Forward the user to the documentation
     * @return Forward the user to the Swagger UI
     */
    @ApiOperation(value = "Documentation",notes = "Swagger REST API Documentation")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody RedirectView forward() {
        return new RedirectView("/swagger-ui/");
    }

}
