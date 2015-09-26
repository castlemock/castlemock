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

package com.fortmocks.war.base.model.project.service;

import com.fortmocks.core.base.model.project.Project;
import com.fortmocks.core.base.model.project.dto.ProjectDto;
import com.fortmocks.war.base.manager.FileManager;
import com.fortmocks.war.base.model.ServiceFacadeImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The project service component is used to assembly all the project service layers and interact with them
 * in order to retrieve a unified answer independent of the project type. The class is responsible for keeping
 * tracks of all the project services and providing the basic functionality shared among all the
 * project services, such as get, delete, update.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.project.Project
 * @see com.fortmocks.core.base.model.project.dto.ProjectDto
 */
@Service
public class ProjectServiceFacadeImpl extends ServiceFacadeImpl<Project, ProjectDto, Long, ProjectServiceImpl<Project, ProjectDto>> implements ProjectServiceFacade {

    @Autowired
    private FileManager fileManager;
    private static final Logger LOGGER = Logger.getLogger(ProjectServiceFacadeImpl.class);

    /**
     * The initiate method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.fortmocks.core.base.model.Service
     * @see com.fortmocks.core.base.model.TypeIdentifier
     * @see com.fortmocks.core.base.model.TypeIdentifiable
     */
    @Override
    public void initiate(){
        initiate(ProjectServiceImpl.class);
    }

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(final String typeUrl, final Long id){
        final ProjectServiceImpl<Project, ProjectDto> service = findByTypeUrl(typeUrl);
        return service.exportProject(id);
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param type The type value for the specific type that the instance belongs to
     * @param multipartFiles The imported project files
     */
    @Override
    public void importProject(final String type, final List<MultipartFile> multipartFiles) {
        final ProjectServiceImpl<Project, ProjectDto> service = findByType(type);
        List<File> files = new ArrayList<File>();
        try {
            files = fileManager.uploadFiles(multipartFiles);

            for(File file : files){
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                final StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }

                service.importProject(stringBuilder.toString());
            }
        } catch (IOException e) {
            LOGGER.error("Unable to import projects", e);
        } finally {
            fileManager.deleteUploadedFiles(files);
        }

    }
}
