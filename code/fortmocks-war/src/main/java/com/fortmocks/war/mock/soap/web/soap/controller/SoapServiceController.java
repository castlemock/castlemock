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

package com.fortmocks.war.mock.soap.web.soap.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SoapServiceController handles all the incoming SOAP request.
 * The SOAP requests will be processed and the correct mocked response
 * will be retrieved from the database. If no response is found, an error
 * response will be returned instead.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/mock/soap/project")
public class SoapServiceController extends AbstractSoapServiceController {


    /**
     * The service is responsible for handling all the incoming SOAP requests. The SOAP requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param request The incoming request that will be processed
     * @return Returns a mocked response
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.SoapMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/{projectId}/**", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String postMethod(@PathVariable final Long projectId, final HttpServletRequest request, final HttpServletResponse response) {
        return process(projectId, request, response);
    }
}
