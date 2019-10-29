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
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestApplicationTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestProjectTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.domain.RestResourceTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.CreateRestMethodOutput;
import com.castlemock.repository.rest.project.RestMethodRepository;
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

    private RestProject restProject = RestProjectTestBuilder.builder().build();
    private RestApplication restApplication = RestApplicationTestBuilder.builder().build();
    private RestResource restResource = RestResourceTestBuilder.builder().build();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestMethod restMethod = RestMethodTestBuilder.builder().build();
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
        final RestMethod restMethod = RestMethodTestBuilder.builder().build();
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
        final RestMethod restMethod = RestMethodTestBuilder.builder().build();
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
