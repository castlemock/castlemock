/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model.project.service;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.UpdateSoapProjectInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.UpdateSoapProjectOutput;
import com.castlemock.web.mock.soap.model.project.SoapProjectDtoGenerator;
import com.castlemock.web.mock.soap.model.project.repository.SoapProjectRepository;
import junit.framework.Assert;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class UpdateSoapProjectServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private UpdateSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapProjectDto soapProject = SoapProjectDtoGenerator.generateSoapProjectDto();
        final UpdateSoapProjectInput input = new UpdateSoapProjectInput(soapProjectDto.getId(), soapProjectDto);
        final ServiceTask<UpdateSoapProjectInput> serviceTask = new ServiceTask<>(input);


        Mockito.when(repository.findOne(Mockito.anyString())).thenReturn(soapProject);
        Mockito.when(repository.save(Mockito.any(SoapProjectDto.class))).thenReturn(soapProject);

        final ServiceResult<UpdateSoapProjectOutput> result = service.process(serviceTask);
        final UpdateSoapProjectOutput output = result.getOutput();
        final SoapProjectDto returnedSoapProjectDto = output.getUpdatedSoapProject();
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(SoapProjectDto.class));
        Assert.assertEquals(soapProject.getId(), returnedSoapProjectDto.getId());
        Assert.assertEquals(soapProject.getName(), returnedSoapProjectDto.getName());
        Assert.assertEquals(soapProject.getDescription(), returnedSoapProjectDto.getDescription());
        Assert.assertEquals(soapProject.getCreated(), returnedSoapProjectDto.getCreated());
        Assert.assertEquals(soapProject.getUpdated(), returnedSoapProjectDto.getUpdated());
    }
}
