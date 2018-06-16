/*
 * Copyright 2018 Karl Dahlgren
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
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapMockResponseOutput;
import com.castlemock.web.mock.soap.model.project.SoapMockResponseGenerator;
import com.castlemock.web.mock.soap.repository.project.SoapMockResponseRepository;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 */
public class UpdateSoapMockResponseServiceTest {


    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private UpdateSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String portId = "PortId";
        final String operation = "OperationId";
        final SoapMockResponse mockResponse = SoapMockResponseGenerator.generateSoapMockResponse();

        final UpdateSoapMockResponseInput input = new UpdateSoapMockResponseInput(projectId, portId, operation, mockResponse.getId(), mockResponse);
        final ServiceTask<UpdateSoapMockResponseInput> serviceTask = new ServiceTask<UpdateSoapMockResponseInput>(input);


        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(mockResponse);
        Mockito.when(mockResponseRepository.update(Mockito.anyString(), Mockito.any(SoapMockResponse.class))).thenReturn(mockResponse);

        final ServiceResult<UpdateSoapMockResponseOutput> result = service.process(serviceTask);
        final UpdateSoapMockResponseOutput output = result.getOutput();
        final SoapMockResponse returnedSoapMockResponse = output.getUpdatedSoapMockResponse();

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).update(mockResponse.getId(), mockResponse);
        Assert.assertEquals(mockResponse.getId(), returnedSoapMockResponse.getId());
        Assert.assertEquals(mockResponse.getName(), returnedSoapMockResponse.getName());
        Assert.assertEquals(mockResponse.getStatus(), returnedSoapMockResponse.getStatus());
        Assert.assertEquals(mockResponse.getBody(), returnedSoapMockResponse.getBody());
        Assert.assertEquals(mockResponse.getHttpStatusCode(), returnedSoapMockResponse.getHttpStatusCode());
        Assert.assertEquals(mockResponse.getStatus(), returnedSoapMockResponse.getStatus());
        Assert.assertEquals(mockResponse.isUsingExpressions(), returnedSoapMockResponse.isUsingExpressions());
        Assert.assertEquals(mockResponse.getXpathExpression(), returnedSoapMockResponse.getXpathExpression());
    }
}
