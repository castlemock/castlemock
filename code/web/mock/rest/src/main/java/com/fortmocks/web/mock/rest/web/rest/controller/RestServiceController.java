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

package com.fortmocks.web.mock.rest.web.rest.controller;

import com.fortmocks.core.basis.model.http.domain.HttpMethod;
import com.fortmocks.core.mock.rest.model.project.domain.RestMockResponse;
import com.fortmocks.core.mock.rest.model.project.domain.RestProject;
import com.fortmocks.core.mock.rest.model.project.domain.RestResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}/application/{applicationId}/**")
    public String getMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.GET, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/{projectId}/application/{applicationId}/**")
    public String postMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.POST, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/{projectId}/application/{applicationId}/**")
    public String putMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.PUT, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, value = "/{projectId}/application/{applicationId}/**")
    public String deleteMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.DELETE, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH, value = "/{projectId}/application/{applicationId}/**")
    public String patchMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.PATCH, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.HEAD, value = "/{projectId}/application/{applicationId}/**")
    public String headMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.HEAD, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.OPTIONS, value = "/{projectId}/application/{applicationId}/**")
    public String optionsMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.OPTIONS, httpServletRequest, httpServletResponse);
    }

    /**
     * The service is responsible for handling all the incoming REST requests. The REST requests will be processed
     * and a response will be generated and returned to the service consumer.
     * @param projectId The id of the project that the request belongs to
     * @param httpServletRequest The incoming request that will be processed
     * @param httpServletResponse The outgoing response
     * @return Returns a mocked response
     * @see RestProject
     * @see RestResource
     * @see RestMockResponse
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.TRACE, value = "/{projectId}/application/{applicationId}/**")
    public String traceMethod(@PathVariable final String projectId, @PathVariable final String applicationId, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return process(projectId, applicationId, HttpMethod.TRACE, httpServletRequest, httpServletResponse);
    }
}
