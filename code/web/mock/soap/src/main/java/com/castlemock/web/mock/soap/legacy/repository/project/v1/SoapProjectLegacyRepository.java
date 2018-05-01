/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.legacy.repository.project.v1;

import com.castlemock.core.mock.soap.legacy.model.project.v1.converter.SoapProjectConverterV1;
import com.castlemock.core.mock.soap.legacy.model.project.v1.domain.SoapProjectV1;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.web.basis.model.AbstractLegacyRepositoryImpl;
import com.castlemock.web.mock.soap.model.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SoapProjectLegacyRepository extends AbstractLegacyRepositoryImpl<SoapProjectV1, SoapProject, String> {

    @Value(value = "${legacy.soap.project.v1.directory}")
    private String projectLegacyFileDirectory;
    @Value(value = "${legacy.soap.resource.v1.directory}")
    private String resourceLegacyFileDirectory;

    @Value(value = "${soap.project.file.extension}")
    private String projectFileExtension;
    @Value(value = "${soap.resource.file.extension}")
    private String resourceFileExtension;
    @Value(value = "${soap.resource.file.directory}")
    private String resourceFileDirectory;



    @Autowired
    private SoapProjectRepository projectRepository;
    @Autowired
    private SoapPortRepository portRepository;
    @Autowired
    private SoapResourceRepository resourceRepository;
    @Autowired
    private SoapOperationRepository operationRepository;
    @Autowired
    private SoapMockResponseRepository mockResponseRepository;



    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     */
    @Override
    public void initialize() {

        final String oldWSDLPath = resourceLegacyFileDirectory + "/wsdl";
        final String newWSDLPath = resourceFileDirectory + "/wsdl";

        // Move the old event files to the new directory
        fileRepositorySupport.moveAllFiles(oldWSDLPath,
                newWSDLPath, resourceFileExtension);


        Collection<SoapProjectV1> projects =
                fileRepositorySupport.load(SoapProjectV1.class, projectLegacyFileDirectory, projectFileExtension);

        for(SoapProjectV1 projectV1 : projects){
            save(projectV1);
            fileRepositorySupport.delete(this.projectLegacyFileDirectory, projectV1.getId() + this.projectFileExtension);
        }
    }

    /**
     * Save an instance.
     * @param type The instance that will be saved.
     */
    @Override
    protected SoapProject save(final SoapProjectV1 type) {
        SoapProject project = SoapProjectConverterV1.convert(type);
        this.projectRepository.save(project);
        for(SoapResource resource : project.getResources()){
            this.resourceRepository.save(resource);
        }
        for(SoapPort port : project.getPorts()){
            this.portRepository.save(port);
            for(SoapOperation operation : port.getOperations()){
                 this.operationRepository.save(operation);
                for(SoapMockResponse mockResponse : operation.getMockResponses()){
                    this.mockResponseRepository.save(mockResponse);
                }
            }
        }
        return project;
    }
}
