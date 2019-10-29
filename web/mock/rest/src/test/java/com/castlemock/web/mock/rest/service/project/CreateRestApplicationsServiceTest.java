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

import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestApplicationTestBuilder;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.repository.rest.project.RestProjectRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

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
    private ImportRestDefinitionService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testProcess(){
        final RestProject restProject = new RestProject();
        restProject.setApplications(new ArrayList<RestApplication>());
        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(restProject);

        final List<RestApplication> restApplications = new ArrayList<RestApplication>();
        for(int index = 0; index < 3; index++){
            final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
            restApplications.add(restApplication);
        }
        /*

        final CreateRestApplicationsInput input = new CreateRestApplicationsInput(restProject.getId(), restApplications);
        final ServiceTask<CreateRestApplicationsInput> serviceTask = new ServiceTask<CreateRestApplicationsInput>(input);
        service.process(serviceTask);

        Mockito.verify(repository, Mockito.timeout(1)).save(Mockito.any(RestProject.class));
        */
    }

}
