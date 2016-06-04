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

package com.castlemock.web.mock.soap.model.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapProjectDtoGenerator {

    public static SoapProjectDto generateSoapProjectDto(){
        final SoapProjectDto projectDto = new SoapProjectDto();
        projectDto.setId("SOAP PROJECT");
        projectDto.setName("Project name");
        projectDto.setDescription("Project description");
        projectDto.setCreated(new Date());
        projectDto.setUpdated(new Date());
        projectDto.setPorts(new ArrayList<SoapPortDto>());
        return projectDto;
    }

    public static SoapProject generateSoapProject(){
        final SoapProject project = new SoapProject();
        project.setId("SOAP PROJECT");
        project.setName("Project name");
        project.setDescription("Project description");
        project.setCreated(new Date());
        project.setUpdated(new Date());
        project.setPorts(new ArrayList<SoapPort>());
        return project;
    }

    public static SoapProject convertDto(final SoapProjectDto soapProjectDto){
        final SoapProject soapProject = new SoapProject();
        soapProject.setId(soapProjectDto.getId());
        soapProject.setName(soapProjectDto.getName());
        soapProject.setDescription(soapProjectDto.getDescription());
        soapProject.setCreated(soapProjectDto.getCreated());
        soapProject.setUpdated(soapProjectDto.getUpdated());
        return soapProject;
    }

    public static SoapProject generateFullSoapProject(){
        final SoapProject soapProject = new SoapProject();
        soapProject.setId("SOAP PROJECT");
        soapProject.setName("Project name");
        soapProject.setDescription("Project description");
        soapProject.setCreated(new Date());
        soapProject.setUpdated(new Date());
        soapProject.setPorts(new ArrayList<SoapPort>());

        for(int portIndex = 0; portIndex < 3; portIndex++){
            final SoapPort soapPort = SoapPortDtoGenerator.generateSoapPort();
            soapPort.setOperations(new ArrayList<SoapOperation>());
            soapProject.getPorts().add(soapPort);

            for(int operationIndex = 0; operationIndex < 3; operationIndex++){
                final SoapOperation soapOperation = SoapOperationDtoGenerator.generateSoapOperation();
                soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());
                soapPort.getOperations().add(soapOperation);

                for(int responseIndex = 0; responseIndex < 3; responseIndex++){
                    final SoapMockResponse soapMockResponse = SoapMockResponseDtoGenerator.generateSoapMockResponse();
                    soapOperation.getMockResponses().add(soapMockResponse);
                }

            }
        }



        return soapProject;
    }

    public static SoapProjectDto generateFullSoapProjectDto(){
        final SoapProjectDto soapProject = new SoapProjectDto();
        soapProject.setId("SOAP PROJECT");
        soapProject.setName("Project name");
        soapProject.setDescription("Project description");
        soapProject.setCreated(new Date());
        soapProject.setUpdated(new Date());
        soapProject.setPorts(new ArrayList<SoapPortDto>());

        for(int portIndex = 0; portIndex < 3; portIndex++){
            final SoapPortDto soapPort = SoapPortDtoGenerator.generateSoapPortDto();
            soapPort.setOperations(new ArrayList<SoapOperationDto>());
            soapProject.getPorts().add(soapPort);

            for(int operationIndex = 0; operationIndex < 3; operationIndex++){
                final SoapOperationDto soapOperation = SoapOperationDtoGenerator.generateSoapOperationDto();
                soapOperation.setMockResponses(new ArrayList<SoapMockResponseDto>());
                soapPort.getOperations().add(soapOperation);

                for(int responseIndex = 0; responseIndex < 3; responseIndex++){
                    final SoapMockResponseDto soapMockResponse = SoapMockResponseDtoGenerator.generateSoapMockResponseDto();
                    soapOperation.getMockResponses().add(soapMockResponse);
                }

            }
        }



        return soapProject;
    }

}
