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
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceGenerator;
import com.castlemock.web.mock.rest.repository.project.RestMethodRepository;
import com.castlemock.web.mock.rest.repository.project.RestMockResponseRepository;
import com.castlemock.web.mock.rest.repository.project.RestResourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

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
        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setId("Resource1");
        restResource1.setUri("/user");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.GET);
        RestMethod restMethod3 = RestMethodGenerator.generateRestMethod();
        restMethod3.setHttpMethod(HttpMethod.PUT);
        restResource1.getMethods().add(restMethod1);
        restResource1.getMethods().add(restMethod2);
        restResource1.getMethods().add(restMethod3);

        RestResource restResource2 = RestResourceGenerator.generateRestResource();
        restResource2.setId("Resource2");
        restResource2.setUri("/user/resource");
        RestMethod restMethod4 = RestMethodGenerator.generateRestMethod();
        restMethod4.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod5 = RestMethodGenerator.generateRestMethod();
        restMethod5.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod4);
        restResource2.getMethods().add(restMethod5);

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1, restMethod2, restMethod3));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Arrays.asList(restMethod4, restMethod5));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod2, output1.getRestMethod());


        IdentifyRestMethodInput input2 =  IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user")
                .httpMethod(HttpMethod.PUT)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod3, output2.getRestMethod());


        IdentifyRestMethodInput input3 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/resource")
                .httpMethod(HttpMethod.POST)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask3 = new ServiceTask<IdentifyRestMethodInput>(input3);
        ServiceResult<IdentifyRestMethodOutput> serviceResult3 = service.process(serviceTask3);
        IdentifyRestMethodOutput output3 = serviceResult3.getOutput();
        Assert.assertEquals(restMethod4, output3.getRestMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessMethodNotFound(){
        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setId("Resource1");
        restResource1.setUri("/user");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.GET);
        RestMethod restMethod3 = RestMethodGenerator.generateRestMethod();
        restMethod3.setHttpMethod(HttpMethod.PUT);
        restResource1.getMethods().add(restMethod1);
        restResource1.getMethods().add(restMethod2);
        restResource1.getMethods().add(restMethod3);

        RestResource restResource2 = RestResourceGenerator.generateRestResource();
        restResource2.setId("Resource2");
        restResource2.setUri("/user/resource");
        RestMethod restMethod4 = RestMethodGenerator.generateRestMethod();
        restMethod4.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod5 = RestMethodGenerator.generateRestMethod();
        restMethod5.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod4);
        restResource2.getMethods().add(restMethod5);

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1, restMethod2, restMethod3));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Arrays.asList(restMethod4, restMethod5));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessResourceNotFound(){
        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setId("Resource1");
        restResource1.setUri("/user");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.GET);
        RestMethod restMethod3 = RestMethodGenerator.generateRestMethod();
        restMethod3.setHttpMethod(HttpMethod.PUT);
        restResource1.getMethods().add(restMethod1);
        restResource1.getMethods().add(restMethod2);
        restResource1.getMethods().add(restMethod3);

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1, restMethod2, restMethod3));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/random")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test
    public void testVariableProcess1(){
        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setId("Resource1");
        restResource1.setUri("/user/{test}");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.GET);

        RestResource restResource2 = RestResourceGenerator.generateRestResource();
        restResource2.setId("Resource2");
        restResource2.setUri("/user/{test}/resource");
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.POST);

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Arrays.asList(restMethod2));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod1, output1.getRestMethod());

        IdentifyRestMethodInput input2 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random/resource")
                .httpMethod(HttpMethod.POST)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod2, output2.getRestMethod());
    }



    @Test
    public void testVariableProcess2(){
        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setId("Resource1");
        restResource1.setUri("/user/{variable}.json");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.GET);

        RestResource restResource2 = RestResourceGenerator.generateRestResource();
        restResource2.setId("Resource2");
        restResource2.setUri("/user/id.{type}");
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.GET);

        RestResource restResource3 = RestResourceGenerator.generateRestResource();
        restResource3.setId("Resource3");
        restResource3.setUri("/resource/{id}...{type}");
        RestMethod restMethod3 = RestMethodGenerator.generateRestMethod();
        restMethod3.setHttpMethod(HttpMethod.GET);

        Mockito.when(resourceRepository.findWithApplicationId("RestApplicationId")).thenReturn(Arrays.asList(restResource1, restResource2, restResource3));

        Mockito.when(methodRepository.findWithResourceId(restResource1.getId())).thenReturn(Arrays.asList(restMethod1));
        Mockito.when(methodRepository.findWithResourceId(restResource2.getId())).thenReturn(Arrays.asList(restMethod2));
        Mockito.when(methodRepository.findWithResourceId(restResource3.getId())).thenReturn(Arrays.asList(restMethod3));

        Mockito.when(mockResponseRepository.findWithMethodId(Mockito.anyString())).thenReturn(new ArrayList<>());

        IdentifyRestMethodInput input1 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/random.json")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod1, output1.getRestMethod());

        IdentifyRestMethodInput input2 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/user/id.xml")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod2, output2.getRestMethod());

        IdentifyRestMethodInput input3 = IdentifyRestMethodInput.builder()
                .restProjectId("RestProjectId")
                .restApplicationId("RestApplicationId")
                .restResourceUri("/resource/test...xml")
                .httpMethod(HttpMethod.GET)
                .build();
        ServiceTask<IdentifyRestMethodInput> serviceTask3 = new ServiceTask<IdentifyRestMethodInput>(input3);
        ServiceResult<IdentifyRestMethodOutput> serviceResult3 = service.process(serviceTask3);
        IdentifyRestMethodOutput output3 = serviceResult3.getOutput();
        Assert.assertEquals(restMethod3, output3.getRestMethod());
    }


}
