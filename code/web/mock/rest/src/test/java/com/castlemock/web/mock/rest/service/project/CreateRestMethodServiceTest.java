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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.CreateRestMethodOutput;
import com.castlemock.web.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.web.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceGenerator;
import com.castlemock.web.mock.rest.repository.project.RestMethodRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestMethodServiceTest {

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private CreateRestMethodService service;

    private RestProject restProject = RestProjectGenerator.generateRestProject();
    private RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
    private RestResource restResource = RestResourceGenerator.generateRestResource();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
        Mockito.when(methodRepository.save(Mockito.any(RestMethod.class))).thenReturn(restMethod);

        final CreateRestMethodInput input = CreateRestMethodInput.builder()
                .projectId(restProject.getId())
                .applicationId(restApplication.getId())
                .resourceId(restResource.getId())
                .method(restMethod)
                .build();
        final ServiceTask<CreateRestMethodInput> serviceTask = new ServiceTask<CreateRestMethodInput>(input);
        final ServiceResult<CreateRestMethodOutput> serviceResult = service.process(serviceTask);
        final CreateRestMethodOutput createRestApplicationOutput = serviceResult.getOutput();
        final RestMethod returnedRestMethod = createRestApplicationOutput.getCreatedRestMethod();

        Assert.assertEquals(restMethod.getName(), returnedRestMethod.getName());
        Assert.assertEquals(restMethod.getHttpMethod(), returnedRestMethod.getHttpMethod());
        Assert.assertEquals(restMethod.getDefaultBody(), returnedRestMethod.getDefaultBody());
        Assert.assertEquals(restMethod.getForwardedEndpoint(), returnedRestMethod.getForwardedEndpoint());
        Assert.assertEquals(restMethod.getStatus(), returnedRestMethod.getStatus());
        Assert.assertEquals(restMethod.getMockResponses(), returnedRestMethod.getMockResponses());
        Assert.assertEquals(restMethod.getResponseStrategy(), returnedRestMethod.getResponseStrategy());
    }

    @Test
    public void testProcessWithoutStatus(){
        final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
        restMethod.setStatus(null);
        Mockito.when(methodRepository.save(Mockito.any(RestMethod.class))).thenReturn(restMethod);

        final CreateRestMethodInput input = CreateRestMethodInput.builder()
                .projectId(restProject.getId())
                .applicationId(restApplication.getId())
                .resourceId(restResource.getId())
                .method(restMethod)
                .build();
        final ServiceTask<CreateRestMethodInput> serviceTask = new ServiceTask<CreateRestMethodInput>(input);
        final ServiceResult<CreateRestMethodOutput> serviceResult = service.process(serviceTask);
        final CreateRestMethodOutput createRestApplicationOutput = serviceResult.getOutput();
        final RestMethod returnedRestMethod = createRestApplicationOutput.getCreatedRestMethod();

        Assert.assertEquals(restMethod.getName(), returnedRestMethod.getName());
        Assert.assertEquals(restMethod.getHttpMethod(), returnedRestMethod.getHttpMethod());
        Assert.assertEquals(restMethod.getDefaultBody(), returnedRestMethod.getDefaultBody());
        Assert.assertEquals(restMethod.getForwardedEndpoint(), returnedRestMethod.getForwardedEndpoint());
        Assert.assertEquals(restMethod.getStatus(), RestMethodStatus.MOCKED);
        Assert.assertEquals(restMethod.getMockResponses(), returnedRestMethod.getMockResponses());
        Assert.assertEquals(restMethod.getResponseStrategy(), returnedRestMethod.getResponseStrategy());
    }

    @Test
    public void testProcessWithoutResponseStrategy(){
        final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
        restMethod.setResponseStrategy(null);
        Mockito.when(methodRepository.save(Mockito.any(RestMethod.class))).thenReturn(restMethod);

        final CreateRestMethodInput input = CreateRestMethodInput.builder()
                .projectId(restProject.getId())
                .applicationId(restApplication.getId())
                .resourceId(restResource.getId())
                .method(restMethod)
                .build();
        final ServiceTask<CreateRestMethodInput> serviceTask = new ServiceTask<CreateRestMethodInput>(input);
        final ServiceResult<CreateRestMethodOutput> serviceResult = service.process(serviceTask);
        final CreateRestMethodOutput createRestApplicationOutput = serviceResult.getOutput();
        final RestMethod returnedRestMethod = createRestApplicationOutput.getCreatedRestMethod();

        Assert.assertEquals(restMethod.getName(), returnedRestMethod.getName());
        Assert.assertEquals(restMethod.getHttpMethod(), returnedRestMethod.getHttpMethod());
        Assert.assertEquals(restMethod.getDefaultBody(), returnedRestMethod.getDefaultBody());
        Assert.assertEquals(restMethod.getForwardedEndpoint(), returnedRestMethod.getForwardedEndpoint());
        Assert.assertEquals(restMethod.getStatus(), returnedRestMethod.getStatus());
        Assert.assertEquals(restMethod.getMockResponses(), returnedRestMethod.getMockResponses());
        Assert.assertEquals(restMethod.getResponseStrategy(), RestResponseStrategy.RANDOM);
    }

}
