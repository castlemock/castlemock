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

package com.fortmocks.web.mock.rest.model.project.repository;

import com.fortmocks.core.mock.rest.model.project.domain.*;
import com.fortmocks.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving REST project from the file system. Each REST project is stored as
 * a separate file. The class also contains the directory and the filename extension for the REST project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestProjectRepository
 * @see RepositoryImpl
 * @see RestProject
 */
@Repository
public class RestProjectRepositoryImpl extends RepositoryImpl<RestProject, Long> implements RestProjectRepository {

    @Value(value = "${rest.project.file.directory}")
    private String restProjectFileDirectory;
    @Value(value = "${rest.project.file.extension}")
    private String restProjectFileExtension;

    private transient Long globalApplicationId = 0L;
    private transient Long globalResourceId = 0L;
    private transient Long globalMethodId = 0L;
    private transient Long globalMockResponseId = 0L;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return restProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return restProjectFileExtension;
    }


    /**
     * The post initiate method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initiate} has finished successful. The method does not contain any functionality and the
     * whole idea is the it should be overridden by subclasses, but only if certain functionality is required to
     * run after the {@link #initiate} method has completed.
     * @see #initiate
     */
    @Override
    protected void postInitiate(){
        Long globalApplicationId = 0L;
        Long globalResourceId = 0L;
        Long globalMethodId = 0L;
        Long globalMockResponseId = 0L;
        List<RestProject> restProjects = findAll();

        for(RestProject restProject : restProjects){
            for(RestApplication restApplication : restProject.getRestApplications()){
                if(restApplication.getId() > globalApplicationId){
                    globalApplicationId = restApplication.getId();
                }
                for(RestResource restResource : restApplication.getRestResources()){
                    if(restResource.getId() > globalResourceId){
                        globalResourceId = restResource.getId();
                    }
                    for(RestMethod restMethod : restResource.getRestMethods()){
                        if(restMethod.getId() > globalMethodId){
                            globalMethodId = restMethod.getId();
                        }
                        for(RestMockResponse restMockResponse : restMethod.getRestMockResponses()){
                            if(restMockResponse.getId() > globalMockResponseId){
                                globalMockResponseId = restMockResponse.getId();
                            }
                        }
                    }

                }
            }
        }
        this.globalApplicationId = globalApplicationId;
        this.globalResourceId = globalResourceId;
        this.globalMethodId = globalMethodId;
        this.globalMockResponseId = globalMockResponseId;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param restProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see RestProject
     */
    @Override
    protected void checkType(RestProject restProject) {

    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param type The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public RestProject save(final RestProject type) {
        for(RestApplication restApplication : type.getRestApplications()){
            if(restApplication.getId() == null){
                Long restApplicationId = getNextRestApplicationId();
                restApplication.setId(restApplicationId);
            }
            for(RestResource restResource : restApplication.getRestResources()){
                if(restResource.getId() == null){
                    Long restResourceId = getNextRestResourceId();
                    restResource.setId(restResourceId);
                }
                for(RestMethod restMethod : restResource.getRestMethods()){
                    if(restMethod.getId() == null){
                        Long restMethodId = getNextRestMethodId();
                        restMethod.setId(restMethodId);
                    }
                    for(RestMockResponse restMockResponse : restMethod.getRestMockResponses()){
                        if(restMockResponse.getId() == null){
                            Long restMockResponseId = getNextRestMockResponseId();
                            restMockResponse.setId(restMockResponseId);
                        }
                    }
                }
            }
        }
        return super.save(type);
    }


    protected synchronized Long getNextRestApplicationId(){
        return ++globalApplicationId;
    }


    protected synchronized Long getNextRestResourceId(){
        return ++globalResourceId;
    }

    protected synchronized Long getNextRestMethodId(){
        return ++globalMethodId;
    }

    protected synchronized Long getNextRestMockResponseId(){
        return ++globalMockResponseId;
    }
}
