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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.service.mock.soap.project.input.UpdateCurrentMockResponseSequenceIndexInput;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";
        final String operationId = "SOAP OPERATION";
        final int responseIndex = 1;

        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();

        Mockito.when(operationRepository.findOne(operationId)).thenReturn(soapOperation);
        Mockito.when(operationRepository.save(Mockito.any())).thenReturn(soapOperation);

        final UpdateCurrentMockResponseSequenceIndexInput input = UpdateCurrentMockResponseSequenceIndexInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .currentResponseSequenceIndex(responseIndex)
                .build();
        final ServiceTask<UpdateCurrentMockResponseSequenceIndexInput> serviceTask = ServiceTask.of(input, "user");
        service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).save(Mockito.eq(soapOperation.toBuilder()
                .currentResponseSequenceIndex(responseIndex)
                .build()));
        Mockito.verify(operationRepository, Mockito.times(1)).findOne(Mockito.eq(operationId));
        Mockito.verifyNoMoreInteractions(operationRepository);
    }
}
