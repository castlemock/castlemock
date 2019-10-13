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

package com.castlemock.web.mock.soap.service.event;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.service.event.input.ReadSoapEventInput;
import com.castlemock.core.mock.soap.service.event.output.ReadSoapEventOutput;

/**
 * The service provides the functionality to retrieve a specific SOAP event from the repository.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapEventService  extends AbstractSoapEventService implements Service<ReadSoapEventInput, ReadSoapEventOutput> {

    @Override
    public ServiceResult<ReadSoapEventOutput> process(ServiceTask<ReadSoapEventInput> serviceTask) {
        final ReadSoapEventInput input = serviceTask.getInput();
        final SoapEvent soapEvent = find(input.getSoapEventId());
        return createServiceResult(ReadSoapEventOutput.builder()
                .soapEvent(soapEvent)
                .build());
    }
}
