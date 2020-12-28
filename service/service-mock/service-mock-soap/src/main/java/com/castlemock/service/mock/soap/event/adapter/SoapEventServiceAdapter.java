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

package com.castlemock.service.mock.soap.event.adapter;

import com.castlemock.model.core.model.ServiceProcessor;
import com.castlemock.model.core.model.TypeIdentifier;
import com.castlemock.model.core.model.event.domain.Event;
import com.castlemock.model.core.service.event.EventServiceAdapter;
import com.castlemock.model.core.service.event.EventServiceFacade;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.service.mock.soap.event.input.ClearAllSoapEventInput;
import com.castlemock.service.mock.soap.event.input.ReadAllSoapEventInput;
import com.castlemock.service.mock.soap.event.input.ReadSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ReadAllSoapEventOutput;
import com.castlemock.service.mock.soap.event.output.ReadSoapEventOutput;
import com.castlemock.service.mock.soap.SoapTypeIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The SOAP event service adapter is an adapter class that provides functionality to
 * translate incoming requests and transform them to the correct service input messages
 * @author Karl Dahlgren
 * @since 1.0
 * @see EventServiceFacade
 */
@Service
public class SoapEventServiceAdapter implements EventServiceAdapter<SoapEvent> {

    private static final String SLASH = "/";
    private static final String WEB = "web";
    private static final String PROJECT = "project";
    private static final String SOAP = "soap";
    private static final String PORT = "port";
    private static final String OPERATION = "operation";


    @Autowired
    private ServiceProcessor serviceProcessor;
    private SoapTypeIdentifier SOAP_TYPE_IDENTIFIER = new SoapTypeIdentifier();

    /**
     * The method provides the functionality to create and store a  instance to a specific service.
     * The service is identified with the provided type value.
     * @param event The instance that will be created
     * @return The saved instance
     */
    @Override
    public SoapEvent create(SoapEvent event) {
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
    public SoapEvent delete(String id) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided  instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param id The id of the instance that will be updated
     * @param event The instance with the new updated values
     * @return The updated instance
     */
    @Override
    public SoapEvent update(String id, SoapEvent event) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<SoapEvent> readAll() {
        final ReadAllSoapEventOutput output = serviceProcessor.process(ReadAllSoapEventInput.builder().build());

        for(SoapEvent soapEvent : output.getSoapEvents()){
            final String resourceLink = generateResourceLink(soapEvent);
            soapEvent.setResourceLink(resourceLink);
        }

        return output.getSoapEvents();
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
    public SoapEvent read(String id) {
        final ReadSoapEventOutput output = serviceProcessor.process(ReadSoapEventInput.builder()
                .soapEventId(id)
                .build());
        return output.getSoapEvent();
    }

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
         return SOAP_TYPE_IDENTIFIER;
    }

    /**
     * The method provides the functionality to convert an instance (parent) to an instance of a subclass to the
     * provided parent.
     * @param parent The parent that will be converted into a subtype of the provided parent
     * @return A new instance of the parent, but as a subtype of the parent
     */
    @Override
    public SoapEvent convertType(Event parent) {
        return new SoapEvent(parent);
    }

    /**
     * The method provides the functionality to generate resource link for an incoming
     * SOAP event. The resource link will be based on the following:
     * <ul>
     *   <li>Project id</li>
     *   <li>Port id</li>
     *   <li>Operation id</li>
     * </ul>
     * @param soapEvent The incoming SOAP event which will be used to generate the resource link
     * @return The resource link generated based on the incoming SOAP event
     */
    @Override
    public String generateResourceLink(SoapEvent soapEvent) {
        return SLASH + WEB + SLASH + SOAP + SLASH + PROJECT + SLASH + soapEvent.getProjectId() + SLASH + PORT + SLASH +
                soapEvent.getPortId() + SLASH + OPERATION + SLASH + soapEvent.getOperationId();
    }

    /**
     * The method will clear and remove all previous events.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        serviceProcessor.process(ClearAllSoapEventInput.builder().build());
    }
}
