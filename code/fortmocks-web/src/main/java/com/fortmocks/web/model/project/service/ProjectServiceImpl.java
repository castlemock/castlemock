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

package com.fortmocks.web.model.project.service;

import com.fortmocks.core.model.TypeConverter;
import com.fortmocks.core.model.TypeIdentifiable;
import com.fortmocks.core.model.TypeIdentifier;
import com.fortmocks.core.model.project.Project;
import com.fortmocks.core.model.project.dto.ProjectDto;
import com.fortmocks.core.model.project.service.ProjectService;

import com.fortmocks.web.model.ServiceImpl;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * The project service is responsible for providing the basic functionality for all the
 * project services.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The project type
 * @param <D> The dto project type
 * @see Project
 * @see ProjectDto
 * @see ProjectService
 */
public abstract class ProjectServiceImpl<T extends Project, D extends ProjectDto> extends ServiceImpl<T, D, Long> implements ProjectService<T, D>, TypeIdentifiable, TypeConverter<ProjectDto, D> {

    @Override
    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        throw new UnsupportedOperationException();
    }
    private static final Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class);

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(final Long id){
        try {
            final Project project = findOneType(id);
            final JAXBContext context = JAXBContext.newInstance(entityClass);
            final Marshaller marshaller = context.createMarshaller();
            final StringWriter writer = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(project, writer);
            return writer.toString();
        }
        catch (JAXBException e) {
            LOGGER.error("Unable to export project with id " + id, e);
        }
        return null;
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     */
    @Override
    public void importProject(final String projectRaw){

        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (projectRaw.getBytes());
            final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            final T project = (T) jaxbUnmarshaller.unmarshal(byteArrayInputStream);
            project.setId(null);
            save(project);
        } catch (JAXBException e) {
            LOGGER.error("Unable to import project", e);
        }
    }
}
