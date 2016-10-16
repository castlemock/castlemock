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

package com.castlemock.web.mock.rest.model.event.service.adapter;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.event.dto.EventDto;
import com.castlemock.core.basis.model.event.service.EventServiceAdapter;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.core.mock.rest.model.event.service.message.input.ClearAllRestEventInput;
import com.castlemock.core.mock.rest.model.event.service.message.input.ReadAllRestEventInput;
import com.castlemock.core.mock.rest.model.event.service.message.input.ReadRestEventInput;
import com.castlemock.core.mock.rest.model.event.service.message.output.ReadAllRestEventOutput;
import com.castlemock.core.mock.rest.model.event.service.message.output.ReadRestEventOutput;
import com.castlemock.web.mock.rest.model.RestTypeIdentifier;
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
public class RestEventServiceAdapter implements EventServiceAdapter<RestEventDto> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private RestTypeIdentifier REST_TYPE_IDENTIFIER = new RestTypeIdentifier();
    private static final String SLASH = "/";
    private static final String WEB = "web";
    private static final String PROJECT = "project";
    private static final String REST = "rest";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String METHOD = "method";

    /**
     * The method provides the functionality to create and store a DTO instance to a specific service.
     * The service is identified with the provided type value.
     * @param dto The instance that will be created
     * @return The saved instance
     */
    @Override
    public RestEventDto create(RestEventDto dto) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param id The id of the instance that will be deleted
     */
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided dto instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param id The id of the instance that will be updated
     * @param dto The instance with the new updated values
     * @return The updated instance
     */
    @Override
    public RestEventDto update(String id, RestEventDto dto) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<RestEventDto> readAll() {
        final ReadAllRestEventOutput output = serviceProcessor.process(new ReadAllRestEventInput());

        for(RestEventDto restEventDto : output.getRestEvents()){
            final String resourceLink = generateResourceLink(restEventDto);
            restEventDto.setResourceLink(resourceLink);
        }

        return output.getRestEvents();
    }

    /**
     * The method is used to retrieve a instance with a specific type. The type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified.
     * @param id The id of the instance that will be retrieved
     * @return A instance that matches the instance type and the provided id. If no instance matches the provided
     *         values, null will be returned.
     */
    @Override
    public RestEventDto read(String id) {
        final ReadRestEventOutput output = serviceProcessor.process(new ReadRestEventInput(id));
        return output.getRestEvent();
    }

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
         return REST_TYPE_IDENTIFIER;
    }

    /**
     * The method provides the functionality to convert an instance (parent) to an instance of a subclass to the
     * provided parent.
     * @param parent The parent that will be converted into a subtype of the provided parent
     * @return A new instance of the parent, but as a subtype of the parent
     */
    @Override
    public RestEventDto convertType(EventDto parent) {
        return new RestEventDto(parent);
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
     * @param restEventDto The incoming REST event which will be used to generate the resource link
     * @return The resource link generated based on the incoming REST event
     */
    @Override
    public String generateResourceLink(RestEventDto restEventDto) {
        return SLASH + WEB + SLASH + REST + SLASH + PROJECT + SLASH + restEventDto.getProjectId() + SLASH + APPLICATION + SLASH + restEventDto.getApplicationId() + SLASH + RESOURCE + SLASH + restEventDto.getResourceId() + SLASH + METHOD + SLASH + restEventDto.getMethodId();
    }

    /**
     * The method will clear and remove all previous events.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        serviceProcessor.process(new ClearAllRestEventInput());
    }

}
