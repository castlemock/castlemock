/*
 * Copyright 2016 Karl Dahlgren
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
import com.castlemock.core.mock.soap.model.event.service.message.input.ClearAllSoapEventInput;
import com.castlemock.core.mock.soap.model.event.service.message.output.ClearAllSoapEventOutput;

/**
 * The service provides the functionality to retrieve all stored SOAP events in the SOAP event repository.
 * @author Karl Dahlgren
 * @since 1.7
 */
@org.springframework.stereotype.Service
public class ClearAllSoapEventService extends AbstractSoapEventService implements Service<ClearAllSoapEventInput, ClearAllSoapEventOutput> {


    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ClearAllSoapEventOutput> process(ServiceTask<ClearAllSoapEventInput> serviceTask) {
        repository.clearAll();
        return createServiceResult(new ClearAllSoapEventOutput());
    }
}
