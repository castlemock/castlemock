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

package com.castlemock.service.mock.rest.event.adapter;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.service.event.EventServiceAdapter;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.service.mock.rest.event.input.ClearAllRestEventInput;
import com.castlemock.service.mock.rest.event.input.ReadAllRestEventInput;
import com.castlemock.service.mock.rest.event.output.ReadAllRestEventOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The REST event service adapter provides the functionality to translate incoming
 * requests and transform them into correct service input messages
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestEventServiceAdapter implements EventServiceAdapter<RestEvent> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private static final String SLASH = "/";
    private static final String WEB = "web";
    private static final String PROJECT = "project";
    private static final String REST = "rest";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String METHOD = "method";

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<RestEvent> readAll() {
        final ReadAllRestEventOutput output = serviceProcessor.process(ReadAllRestEventInput.builder().build());

        for(RestEvent restEvent : output.getRestEvents()){
            final String resourceLink = generateResourceLink(restEvent);
            restEvent.setResourceLink(resourceLink);
        }

        return output.getRestEvents();
    }

    @Override
    public String getType() {
        return "rest";
    }

    /**
     * The method provides the functionality to generate resource link for an incoming
     * REST event. The resource link will be based on the following:
     * <ul>
     *   <li>Project id</li>
     *   <li>Application id</li>
     *   <li>Resource id</li>
     *   <li>Method id</li>
     * </ul>
     * @param restEvent The incoming REST event which will be used to generate the resource link
     * @return The resource link generated based on the incoming REST event
     */
    @Override
    public String generateResourceLink(RestEvent restEvent) {
        return SLASH + WEB + SLASH + REST + SLASH + PROJECT + SLASH + restEvent.getProjectId() + SLASH + APPLICATION + SLASH + restEvent.getApplicationId() + SLASH + RESOURCE + SLASH + restEvent.getResourceId() + SLASH + METHOD + SLASH + restEvent.getMethodId();
    }

    /**
     * The method will clear and remove all previous events.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        serviceProcessor.process(ClearAllRestEventInput.builder().build());
    }

}
