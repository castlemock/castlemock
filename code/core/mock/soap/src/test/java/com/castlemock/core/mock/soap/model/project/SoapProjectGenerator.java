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

package com.castlemock.core.mock.soap.model.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapProjectGenerator {

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

    public static SoapProject generateFullSoapProject(){
        final SoapProject soapProject = new SoapProject();
        soapProject.setId("SOAP PROJECT");
        soapProject.setName("Project name");
        soapProject.setDescription("Project description");
        soapProject.setCreated(new Date());
        soapProject.setUpdated(new Date());
        soapProject.setPorts(new ArrayList<SoapPort>());

        for(int portIndex = 0; portIndex < 3; portIndex++){
            final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
            soapPort.setOperations(new ArrayList<SoapOperation>());
            soapProject.getPorts().add(soapPort);

            for(int operationIndex = 0; operationIndex < 3; operationIndex++){
                final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
                soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());
                soapPort.getOperations().add(soapOperation);

                for(int responseIndex = 0; responseIndex < 3; responseIndex++){
                    final SoapMockResponse soapMockResponse = SoapMockResponseGenerator.generateSoapMockResponse();
                    soapOperation.getMockResponses().add(soapMockResponse);
                }

            }
        }

        return soapProject;
    }

}
