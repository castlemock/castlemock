/*
 * Copyright 2017 Karl Dahlgren
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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.domain.RestResourceTestBuilder;
import com.castlemock.core.mock.rest.service.project.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.IdentifyRestMethodOutput;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Karl Dahlgren
 * @since 1.8
 */
public class IdentifyRestMethodServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private IdentifyRestMethodService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestMethod restMethod1 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.POST)
                .build();
        final RestMethod restMethod2 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.GET)
                .build();
        final RestMethod restMethod3 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.PUT)
                .build();
        final RestResource restResource1 = RestResourceTestBuilder.builder()
                .id("Resource1")
                .uri("/user")
                .methods(ImmutableList.of(restMethod1, restMethod2, restMethod3))
                .build();


        final RestMethod restMethod4 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.POST)
                .build();
        final RestMethod restMethod5 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.GET)
                .build();
        final RestResource restResource2 = RestResourceTestBuilder.builder()
                .id("Resource2")
                .uri("/user/resource")
                .methods(ImmutableList.of(restMethod4, restMethod5))
                .build();

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId"))
                .thenReturn(Arrays.asList(restResource1, restResource2));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId()))
                .thenReturn(Arrays.asList(restMethod1, restMethod2, restMethod3));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId()))
                .thenReturn(Arrays.asList(restMethod4, restMethod5));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        final IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<>(input1);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        final IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod2, output1.getRestMethod());


        final IdentifyRestMethodInput input2 =  IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user")
                .httpMethod(HttpMethod.PUT)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<>(input2);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        final IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod3, output2.getRestMethod());


        final IdentifyRestMethodInput input3 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/resource")
                .httpMethod(HttpMethod.POST)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask3 = new ServiceTask<IdentifyRestMethodInput>(input3);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult3 = service.process(serviceTask3);
        final IdentifyRestMethodOutput output3 = serviceResult3.getOutput();
        Assert.assertEquals(restMethod4, output3.getRestMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessMethodNotFound(){
        final RestMethod restMethod1 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.POST)
                .build();
        final RestMethod restMethod2 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.GET)
                .build();
        final RestMethod restMethod3 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.PUT)
                .build();

        final RestResource restResource1 = RestResourceTestBuilder.builder()
                .id("Resource1")
                .uri("/user")
                .methods(ImmutableList.of(restMethod1, restMethod2, restMethod3))
                .build();

        final RestMethod restMethod4 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.POST)
                .build();
        final RestMethod restMethod5 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.GET)
                .build();

        final RestResource restResource2 = RestResourceTestBuilder.builder()
                .id("Resource2")
                .uri("/user/resource")
                .methods(ImmutableList.of(restMethod4, restMethod5))
                .build();

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1, restMethod2, restMethod3));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Arrays.asList(restMethod4, restMethod5));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        final IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessResourceNotFound(){
        final RestMethod restMethod1 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.POST)
                .build();
        final RestMethod restMethod2 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.GET)
                .build();
        final RestMethod restMethod3 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.PUT)
                .build();
        final RestResource restResource1 = RestResourceTestBuilder.builder()
                .id("Resource1")
                .uri("/user")
                .methods(ImmutableList.of(restMethod1, restMethod2, restMethod3))
                .build();

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1, restMethod2, restMethod3));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        final IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/random")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test
    public void testVariableProcess1(){
        final RestResource restResource1 = RestResourceTestBuilder.builder()
                .id("Resource1")
                .uri("/user/{test}")
                .build();
        final RestMethod restMethod1 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.GET)
                .build();
        final RestResource restResource2 = RestResourceTestBuilder.builder()
                .id("Resource2")
                .uri("/user/{test}/resource")
                .build();
        final RestMethod restMethod2 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.POST)
                .build();

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Arrays.asList(restMethod2));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        final IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        final IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod1, output1.getRestMethod());

        final IdentifyRestMethodInput input2 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random/resource")
                .httpMethod(HttpMethod.POST)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        final IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod2, output2.getRestMethod());
    }

    @Test
    public void testVariableProcess2(){
        final RestResource restResource1 = RestResourceTestBuilder.builder()
                .id("Resource1")
                .uri("/user/{variable}.json")
                .build();
        final RestMethod restMethod1 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.GET)
                .build();

        final RestResource restResource2 = RestResourceTestBuilder.builder()
                .id("Resource2")
                .uri("/user/id.{type}")
                .build();
        final RestMethod restMethod2 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.GET)
                .build();

        final RestResource restResource3 = RestResourceTestBuilder.builder()
                .id("Resource3")
                .uri("/resource/{id}...{type}")
                .build();
        final RestMethod restMethod3 = RestMethodTestBuilder.builder()
                .resourceId("Resource3")
                .httpMethod(HttpMethod.GET)
                .build();

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2, restResource3));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Collections.singletonList(restMethod1));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Collections.singletonList(restMethod2));
        Mockito.when(methodRepository.findWithResourceId(restResource3.getId())).thenReturn(Collections.singletonList(restMethod3));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        final IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random.json")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        final IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod1, output1.getRestMethod());

        final IdentifyRestMethodInput input2 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/id.xml")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        final IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod2, output2.getRestMethod());

        final IdentifyRestMethodInput input3 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/resource/test...xml")
                .httpMethod(HttpMethod.GET)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask3 = new ServiceTask<IdentifyRestMethodInput>(input3);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult3 = service.process(serviceTask3);
        final IdentifyRestMethodOutput output3 = serviceResult3.getOutput();
        Assert.assertEquals(restMethod3, output3.getRestMethod());
    }

    @Test
    public void testProcessSamePath(){
        final RestMethod restMethod1 = RestMethodTestBuilder.builder()
                .resourceId("Resource1")
                .httpMethod(HttpMethod.GET)
                .build();
        final RestResource restResource1 = RestResourceTestBuilder.builder()
                .id("Resource1")
                .uri("/user/{userId}")
                .methods(ImmutableList.of(restMethod1))
                .build();

        final RestMethod restMethod2 = RestMethodTestBuilder.builder()
                .resourceId("Resource2")
                .httpMethod(HttpMethod.POST)
                .build();
        final RestResource restResource2 = RestResourceTestBuilder.builder()
                .id("Resource2")
                .uri("/user/search")
                .methods(ImmutableList.of(restMethod1))
                .build();

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2));
        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Collections.singletonList(restMethod1));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Collections.singletonList(restMethod2));
        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        final IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/search")
                .httpMethod(HttpMethod.POST)
                .build();
        final ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        final ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        final IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod2, output1.getRestMethod());
    }

}
