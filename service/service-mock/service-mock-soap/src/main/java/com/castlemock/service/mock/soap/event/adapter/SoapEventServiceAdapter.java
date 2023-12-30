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

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.service.event.EventServiceAdapter;
import com.castlemock.model.core.service.event.EventServiceFacade;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.service.mock.soap.event.input.ClearAllSoapEventInput;
import com.castlemock.service.mock.soap.event.input.ReadAllSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ReadAllSoapEventOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<SoapEvent> readAll() {
        final ReadAllSoapEventOutput output = serviceProcessor.process(ReadAllSoapEventInput.builder().build());
        return output.getSoapEvents().stream()
                .map(event -> event.toBuilder()
                        .resourceLink(generateResourceLink(event))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String getType() {
        return "soap";
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
