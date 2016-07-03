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

package com.castlemock.web.mock.rest.model.project.service;

import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.web.mock.rest.model.project.RestApplicationDtoGenerator;
import com.castlemock.web.mock.rest.model.project.repository.RestProjectRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestApplicationsServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private CreateRestApplicationsService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testProcess(){
        final RestProjectDto restProject = new RestProjectDto();
        restProject.setApplications(new ArrayList<RestApplicationDto>());
        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(restProject);

        final List<RestApplicationDto> restApplications = new ArrayList<RestApplicationDto>();
        for(int index = 0; index < 3; index++){
            final RestApplicationDto restApplicationDto = RestApplicationDtoGenerator.generateRestApplicationDto();
            restApplications.add(restApplicationDto);
        }
        /*

        final CreateRestApplicationsInput input = new CreateRestApplicationsInput(restProject.getId(), restApplications);
        final ServiceTask<CreateRestApplicationsInput> serviceTask = new ServiceTask<CreateRestApplicationsInput>(input);
        service.process(serviceTask);

        Mockito.verify(repository, Mockito.timeout(1)).save(Mockito.any(RestProjectDto.class));
        */
    }

}
