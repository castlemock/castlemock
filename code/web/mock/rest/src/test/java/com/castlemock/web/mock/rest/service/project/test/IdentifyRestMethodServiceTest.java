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


package com.castlemock.web.mock.rest.service.project.test;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.web.mock.rest.model.project.RestMethodGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceGenerator;
import com.castlemock.web.mock.rest.repository.project.RestProjectRepository;
import com.castlemock.web.mock.rest.service.project.IdentifyRestMethodService;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.8
 */
public class IdentifyRestMethodServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private IdentifyRestMethodService service;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testProcess(){
        RestApplication restApplication = RestApplicationGenerator.generateRestApplication();

        RestResource restResource1 = RestResourceGenerator.generateRestResource();
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
        restResource2.setUri("/user/resource");
        RestMethod restMethod4 = RestMethodGenerator.generateRestMethod();
        restMethod4.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod5 = RestMethodGenerator.generateRestMethod();
        restMethod5.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod4);
        restResource2.getMethods().add(restMethod5);

        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);


        //Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod2, output1.getRestMethod());


        IdentifyRestMethodInput input2 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user", HttpMethod.PUT);
        ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod3, output2.getRestMethod());


        IdentifyRestMethodInput input3 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/resource", HttpMethod.POST);
        ServiceTask<IdentifyRestMethodInput> serviceTask3 = new ServiceTask<IdentifyRestMethodInput>(input3);
        ServiceResult<IdentifyRestMethodOutput> serviceResult3 = service.process(serviceTask3);
        IdentifyRestMethodOutput output3 = serviceResult3.getOutput();
        Assert.assertEquals(restMethod4, output3.getRestMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void testProcessMethodNotFound(){
        RestApplication restApplication = RestApplicationGenerator.generateRestApplication();

        RestResource restResource1 = RestResourceGenerator.generateRestResource();
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
        restResource2.setUri("/user/resource");
        RestMethod restMethod4 = RestMethodGenerator.generateRestMethod();
        restMethod4.setHttpMethod(HttpMethod.POST);
        RestMethod restMethod5 = RestMethodGenerator.generateRestMethod();
        restMethod5.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod4);
        restResource2.getMethods().add(restMethod5);

        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);


        //Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/random", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void testProcessResourceNotFound(){
        RestApplication restApplication = RestApplicationGenerator.generateRestApplication();

        RestResource restResource1 = RestResourceGenerator.generateRestResource();
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


        restApplication.getResources().add(restResource1);

        //Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/random", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test
    @Ignore
    public void testVariableProcess1(){
        RestApplication restApplication = RestApplicationGenerator.generateRestApplication();

        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setUri("/user/{test}");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.GET);
        restResource1.getMethods().add(restMethod1);


        RestResource restResource2 = RestResourceGenerator.generateRestResource();
        restResource2.setUri("/user/{test}/resource");
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.POST);
        restResource2.getMethods().add(restMethod2);

        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);


        //Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/random", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod1, output1.getRestMethod());

        IdentifyRestMethodInput input2 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/random/resource", HttpMethod.POST);
        ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod2, output2.getRestMethod());
    }



    @Test
    @Ignore
    public void testVariableProcess2(){
        RestApplication restApplication = RestApplicationGenerator.generateRestApplication();

        RestResource restResource1 = RestResourceGenerator.generateRestResource();
        restResource1.setUri("/user/{variable}.json");
        RestMethod restMethod1 = RestMethodGenerator.generateRestMethod();
        restMethod1.setHttpMethod(HttpMethod.GET);
        restResource1.getMethods().add(restMethod1);

        RestResource restResource2 = RestResourceGenerator.generateRestResource();
        restResource2.setUri("/user/id.{type}");
        RestMethod restMethod2 = RestMethodGenerator.generateRestMethod();
        restMethod2.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod2);

        RestResource restResource3 = RestResourceGenerator.generateRestResource();
        restResource3.setUri("/resource/{id}...{type}");
        RestMethod restMethod3 = RestMethodGenerator.generateRestMethod();
        restMethod3.setHttpMethod(HttpMethod.GET);
        restResource3.getMethods().add(restMethod3);


        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);
        restApplication.getResources().add(restResource3);

        //Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/random.json", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        ServiceResult<IdentifyRestMethodOutput> serviceResult1 = service.process(serviceTask1);
        IdentifyRestMethodOutput output1 = serviceResult1.getOutput();
        Assert.assertEquals(restMethod1, output1.getRestMethod());

        IdentifyRestMethodInput input2 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/id.xml", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask2 = new ServiceTask<IdentifyRestMethodInput>(input2);
        ServiceResult<IdentifyRestMethodOutput> serviceResult2 = service.process(serviceTask2);
        IdentifyRestMethodOutput output2 = serviceResult2.getOutput();
        Assert.assertEquals(restMethod2, output2.getRestMethod());

        IdentifyRestMethodInput input3 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/resource/test...xml", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask3 = new ServiceTask<IdentifyRestMethodInput>(input3);
        ServiceResult<IdentifyRestMethodOutput> serviceResult3 = service.process(serviceTask3);
        IdentifyRestMethodOutput output3 = serviceResult3.getOutput();
        Assert.assertEquals(restMethod3, output3.getRestMethod());
    }


}
