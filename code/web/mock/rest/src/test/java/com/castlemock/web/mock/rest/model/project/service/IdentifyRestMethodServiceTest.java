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


package com.castlemock.web.mock.rest.model.project.service;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.IdentifyRestMethodOutput;
import com.castlemock.web.mock.rest.model.project.RestApplicationDtoGenerator;
import com.castlemock.web.mock.rest.model.project.RestMethodDtoGenerator;
import com.castlemock.web.mock.rest.model.project.RestResourceDtoGenerator;
import com.castlemock.web.mock.rest.model.project.repository.RestProjectRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
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
    public void testProcess(){
        RestApplicationDto restApplication = RestApplicationDtoGenerator.generateRestApplicationDto();

        RestResourceDto restResource1 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource1.setUri("/user");
        RestMethodDto restMethod1 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod1.setHttpMethod(HttpMethod.POST);
        RestMethodDto restMethod2 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod2.setHttpMethod(HttpMethod.GET);
        RestMethodDto restMethod3 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod3.setHttpMethod(HttpMethod.PUT);
        restResource1.getMethods().add(restMethod1);
        restResource1.getMethods().add(restMethod2);
        restResource1.getMethods().add(restMethod3);

        RestResourceDto restResource2 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource2.setUri("/user/resource");
        RestMethodDto restMethod4 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod4.setHttpMethod(HttpMethod.POST);
        RestMethodDto restMethod5 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod5.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod4);
        restResource2.getMethods().add(restMethod5);

        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);


        Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

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
    public void testProcessMethodNotFound(){
        RestApplicationDto restApplication = RestApplicationDtoGenerator.generateRestApplicationDto();

        RestResourceDto restResource1 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource1.setUri("/user");
        RestMethodDto restMethod1 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod1.setHttpMethod(HttpMethod.POST);
        RestMethodDto restMethod2 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod2.setHttpMethod(HttpMethod.GET);
        RestMethodDto restMethod3 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod3.setHttpMethod(HttpMethod.PUT);
        restResource1.getMethods().add(restMethod1);
        restResource1.getMethods().add(restMethod2);
        restResource1.getMethods().add(restMethod3);

        RestResourceDto restResource2 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource2.setUri("/user/resource");
        RestMethodDto restMethod4 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod4.setHttpMethod(HttpMethod.POST);
        RestMethodDto restMethod5 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod5.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod4);
        restResource2.getMethods().add(restMethod5);

        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);


        Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/user/random", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessResourceNotFound(){
        RestApplicationDto restApplication = RestApplicationDtoGenerator.generateRestApplicationDto();

        RestResourceDto restResource1 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource1.setUri("/user");
        RestMethodDto restMethod1 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod1.setHttpMethod(HttpMethod.POST);
        RestMethodDto restMethod2 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod2.setHttpMethod(HttpMethod.GET);
        RestMethodDto restMethod3 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod3.setHttpMethod(HttpMethod.PUT);
        restResource1.getMethods().add(restMethod1);
        restResource1.getMethods().add(restMethod2);
        restResource1.getMethods().add(restMethod3);


        restApplication.getResources().add(restResource1);

        Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

        IdentifyRestMethodInput input1 = new IdentifyRestMethodInput("RestProjectId", "RestApplicationId", "/random", HttpMethod.GET);
        ServiceTask<IdentifyRestMethodInput> serviceTask1 = new ServiceTask<IdentifyRestMethodInput>(input1);
        service.process(serviceTask1);
    }

    @Test
    public void testVariableProcess1(){
        RestApplicationDto restApplication = RestApplicationDtoGenerator.generateRestApplicationDto();

        RestResourceDto restResource1 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource1.setUri("/user/{test}");
        RestMethodDto restMethod1 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod1.setHttpMethod(HttpMethod.GET);
        restResource1.getMethods().add(restMethod1);


        RestResourceDto restResource2 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource2.setUri("/user/{test}/resource");
        RestMethodDto restMethod2 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod2.setHttpMethod(HttpMethod.POST);
        restResource2.getMethods().add(restMethod2);

        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);


        Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

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
    public void testVariableProcess2(){
        RestApplicationDto restApplication = RestApplicationDtoGenerator.generateRestApplicationDto();

        RestResourceDto restResource1 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource1.setUri("/user/{variable}.json");
        RestMethodDto restMethod1 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod1.setHttpMethod(HttpMethod.GET);
        restResource1.getMethods().add(restMethod1);

        RestResourceDto restResource2 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource2.setUri("/user/id.{type}");
        RestMethodDto restMethod2 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod2.setHttpMethod(HttpMethod.GET);
        restResource2.getMethods().add(restMethod2);

        RestResourceDto restResource3 = RestResourceDtoGenerator.generateRestResourceDto();
        restResource3.setUri("/resource/{id}...{type}");
        RestMethodDto restMethod3 = RestMethodDtoGenerator.generateRestMethodDto();
        restMethod3.setHttpMethod(HttpMethod.GET);
        restResource3.getMethods().add(restMethod3);


        restApplication.getResources().add(restResource1);
        restApplication.getResources().add(restResource2);
        restApplication.getResources().add(restResource3);

        Mockito.when(repository.findRestApplication(Mockito.anyString(), Mockito.anyString())).thenReturn(restApplication);

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
