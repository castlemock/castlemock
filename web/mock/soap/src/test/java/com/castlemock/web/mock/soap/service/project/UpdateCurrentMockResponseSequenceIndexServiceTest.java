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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.service.project.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateCurrentMockResponseSequenceIndexOutput;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class UpdateCurrentMockResponseSequenceIndexServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateCurrentMockResponseSequenceIndexService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";
        final String operationId = "SOAP OPERATION";
        final int responseIndex = 1;

        final UpdateCurrentMockResponseSequenceIndexInput input = UpdateCurrentMockResponseSequenceIndexInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .currentResponseSequenceIndex(responseIndex)
                .build();
        final ServiceTask<UpdateCurrentMockResponseSequenceIndexInput> serviceTask = new ServiceTask<UpdateCurrentMockResponseSequenceIndexInput>(input);
        final ServiceResult<UpdateCurrentMockResponseSequenceIndexOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).setCurrentResponseSequenceIndex(operationId, responseIndex);
    }
}
