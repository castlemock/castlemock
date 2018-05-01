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

package com.castlemock.web.mock.rest.model.project;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.domain.HttpParameter;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.model.event.domain.RestRequest;
import com.castlemock.core.mock.rest.model.event.domain.RestResponse;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestEventGenerator {

    public static RestEvent generateRestEvent(){
        final RestEvent event = new RestEvent();
        event.setId("REST PROJECT");
        event.setMethodId("REST method id");
        event.setEndDate(new Date());
        event.setStartDate(new Date());
        event.setProjectId("ProjectId");
        event.setResourceId("ResourceId");
        event.setApplicationId("ApplicationId");

        RestRequest restRequest = new RestRequest();
        restRequest.setHttpMethod(HttpMethod.GET);
        restRequest.setBody("REST request body");
        restRequest.setContentType("application/json");
        restRequest.setHttpHeaders(new ArrayList<HttpHeader>());
        restRequest.setHttpParameters(new ArrayList<HttpParameter>());
        restRequest.setUri("URI");

        RestResponse restResponse = new RestResponse();
        restResponse.setHttpStatusCode(200);
        restResponse.setContentType("application/json");
        restResponse.setBody("REST response body");
        restResponse.setMockResponseName("Mock response name");

        event.setRequest(restRequest);
        event.setResponse(restResponse);

        return event;
    }

}
