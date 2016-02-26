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

package com.fortmocks.web.mock.rest.model.project.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.core.mock.rest.model.project.domain.RestProject;
import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.CreateRestApplicationInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.CreateRestApplicationOutput;
import com.fortmocks.web.mock.rest.model.project.RestApplicationDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestApplicationServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateRestApplicationService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject restProject = new RestProject();
        restProject.setApplications(new ArrayList<RestApplication>());
        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(restProject);
        final RestApplicationDto restApplicationDto = RestApplicationDtoGenerator.generateRestApplicationDto();
        restApplicationDto.setId(null);

        final CreateRestApplicationInput input = new CreateRestApplicationInput("ProjectId", restApplicationDto);
        input.setRestApplication(restApplicationDto);
        input.setRestProjectId("ProjectId");

        final ServiceTask<CreateRestApplicationInput> serviceTask = new ServiceTask<CreateRestApplicationInput>(input);
        final ServiceResult<CreateRestApplicationOutput> serviceResult = service.process(serviceTask);
        final CreateRestApplicationOutput createRestApplicationOutput = serviceResult.getOutput();
        final RestApplicationDto returnedRestApplicationDto = createRestApplicationOutput.getSavedRestApplication();

        Assert.assertEquals(restApplicationDto.getName(), returnedRestApplicationDto.getName());
        Assert.assertEquals(restApplicationDto.getResources(), returnedRestApplicationDto.getResources());
    }

}