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

package com.castlemock.web.mock.soap.model.event.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.castlemock.core.mock.soap.model.event.service.message.output.CreateSoapEventOutput;
import org.springframework.beans.factory.annotation.Value;

/**
 * The service provides the functionality to create new SOAP event and save it to the repository.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapEventService extends AbstractSoapEventService implements Service<CreateSoapEventInput, CreateSoapEventOutput> {

    @Value("${soap.event.max}")
    private Integer soapMaxEventCount;

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateSoapEventOutput> process(ServiceTask<CreateSoapEventInput> serviceTask) {
        final CreateSoapEventInput input = serviceTask.getInput();
        final SoapEventDto soapEventDto = input.getSoapEvent();
        if(count() >= soapMaxEventCount){
            repository.deleteOldestEvent();
        }
        final SoapEventDto createdSoapEvent = save(soapEventDto);
        return createServiceResult(new CreateSoapEventOutput(createdSoapEvent));
    }
}
