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


package com.castlemock.web.mock.graphql.web.graphql.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mock/graphql/project")
public class GraphQLServiceController extends AbstractGraphQLServiceController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/{projectId}/application/{applicationId}/**")
    public ResponseEntity postMethod(@PathVariable final String projectId,
                                     @PathVariable final String applicationId,
                                     final HttpServletRequest httpServletRequest,
                                     final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, httpServletRequest, httpServletResponse);
    }

}
