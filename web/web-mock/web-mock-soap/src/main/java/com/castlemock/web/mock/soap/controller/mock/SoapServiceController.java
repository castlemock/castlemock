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

package com.castlemock.web.mock.soap.controller.mock;

import com.castlemock.model.core.model.ServiceProcessor;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
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

    @Autowired
    public SoapServiceController(final ServiceProcessor serviceProcessor, final ServletContext servletContext){
        super(serviceProcessor, servletContext);
    }

    /**
     * The service is responsible for handling all the incoming SOAP requests. The SOAP requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param request The incoming request that will be processed
     * @param response The outgoing response
     * @return Returns a mocked response
     * @see SoapProject
     * @see SoapOperation
     * @see SoapMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/{projectId}/**")
    public ResponseEntity<?> postMethod(@PathVariable final String projectId, final HttpServletRequest request, final HttpServletResponse response) {
        return process(projectId, request, response);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}", produces = {MediaType.TEXT_XML_VALUE})
    public ResponseEntity<?> getMethod(@PathVariable final String projectId, final HttpServletRequest request, final HttpServletResponse response) {
        return processGet(projectId, request, response);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}/**", produces = {MediaType.TEXT_XML_VALUE})
    public ResponseEntity<?> getWildcardMethod(@PathVariable final String projectId, final HttpServletRequest request, final HttpServletResponse response) {
        return processGet(projectId, request, response);
    }
}
