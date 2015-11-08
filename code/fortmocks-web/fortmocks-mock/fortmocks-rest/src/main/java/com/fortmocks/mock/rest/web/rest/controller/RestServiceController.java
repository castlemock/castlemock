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

package com.fortmocks.mock.rest.web.rest.controller;

import com.fortmocks.mock.rest.model.project.RestMethodType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The RestServiceController handles all the incoming REST request.
 * The REST requests will be processed and the correct mocked response
 * will be retrieved from the database. If no response is found, an error
 * response will be returned instead.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/mock/rest/project")
public class RestServiceController extends AbstractRestServiceController  {


    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see com.fortmocks.mock.rest.model.project.RestProject
     * @see com.fortmocks.mock.rest.model.project.RestResource
     * @see com.fortmocks.mock.rest.model.project.RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}/application/{applicationId}/**", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String getMethod(@PathVariable final Long projectId, @PathVariable final Long applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, RestMethodType.GET, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see com.fortmocks.mock.rest.model.project.RestProject
     * @see com.fortmocks.mock.rest.model.project.RestResource
     * @see com.fortmocks.mock.rest.model.project.RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/{projectId}/application/{applicationId}/**", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String postMethod(@PathVariable final Long projectId, @PathVariable final Long applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, RestMethodType.POST, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see com.fortmocks.mock.rest.model.project.RestProject
     * @see com.fortmocks.mock.rest.model.project.RestResource
     * @see com.fortmocks.mock.rest.model.project.RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/{projectId}/application/{applicationId}/**", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String putMethod(@PathVariable final Long projectId, @PathVariable final Long applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, RestMethodType.PUT, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see com.fortmocks.mock.rest.model.project.RestProject
     * @see com.fortmocks.mock.rest.model.project.RestResource
     * @see com.fortmocks.mock.rest.model.project.RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, value = "/{projectId}/application/{applicationId}/**", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String deleteMethod(@PathVariable final Long projectId, @PathVariable final Long applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, RestMethodType.DELETE, httpServletRequest, httpServletResponse);
    }

}
