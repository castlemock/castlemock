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

package com.castlemock.web.mock.rest.legacy.repository.project.v1;

import com.castlemock.core.mock.rest.legacy.model.project.v1.convert.RestProjectConverterV1;
import com.castlemock.core.mock.rest.legacy.model.project.v1.domain.RestProjectV1;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.web.basis.model.AbstractLegacyRepositoryImpl;
import com.castlemock.web.mock.rest.model.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class RestProjectLegacyRepository extends AbstractLegacyRepositoryImpl<RestProjectV1, RestProject, String> {

    @Value(value = "${legacy.rest.project.v1.directory}")
    private String fileDirectory;
    @Value(value = "${rest.project.file.extension}")
    private String fileExtension;

    @Autowired
    private RestProjectRepository projectRepository;
    @Autowired
    private RestApplicationRepository applicationRepository;
    @Autowired
    private RestResourceRepository resourceRepository;
    @Autowired
    private RestMethodRepository methodRepository;
    @Autowired
    private RestMockResponseRepository mockResponseRepository;



    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     */
    @Override
    public void initialize() {
        Collection<RestProjectV1> projects =
                fileRepositorySupport.load(RestProjectV1.class, fileDirectory, fileExtension);

        for(RestProjectV1 projectV1 : projects){
            save(projectV1);
            fileRepositorySupport.delete(this.fileDirectory, projectV1.getId() + this.fileExtension);
        }
    }


    /**
     * Save an instance.
     * @param type The instance that will be saved.
     */
    @Override
    protected RestProject save(RestProjectV1 type) {
        RestProject project = RestProjectConverterV1.convert(type);
        this.projectRepository.save(project);
        for(RestApplication application : project.getApplications()) {
            this.applicationRepository.save(application);
            for (RestResource resource : application.getResources()) {
                this.resourceRepository.save(resource);
                for (RestMethod method : resource.getMethods()) {
                    this.methodRepository.save(method);
                    for (RestMockResponse mockResponse : method.getMockResponses()) {
                        this.mockResponseRepository.save(mockResponse);
                    }
                }
            }
        }
        return project;
    }
}
