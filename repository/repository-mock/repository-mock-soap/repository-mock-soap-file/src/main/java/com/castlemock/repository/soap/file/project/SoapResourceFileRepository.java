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


package com.castlemock.repository.soap.file.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.FileRepositorySupport;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.google.common.base.Preconditions;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.*;

@Repository
@Profile(Profiles.FILE)
public class SoapResourceFileRepository extends FileRepository<SoapResourceFileRepository.SoapResourceFile, SoapResource, String> implements SoapResourceRepository {

    private static final String WSDL_DIRECTORY = "wsdl";
    private static final String SCHEMA_DIRECTORY = "schema";

    @Value(value = "${soap.resource.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.resource.file.extension}")
    private String fileExtension;

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapResourceFileRepository.class);

    @Autowired
    private FileRepositorySupport fileRepositorySupport;
    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return fileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * The method is abstract and every subclass is responsible for overriding the method and provided the postfix
     * for their corresponding file type.
     *
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return fileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(SoapResourceFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapResource> search(SearchQuery query) {
        final List<SoapResource> result = new LinkedList<SoapResource>();
        for(SoapResourceFile soapResourceFile : collection.values()){
            if(SearchValidator.validate(soapResourceFile.getName(), query.getQuery())){
                SoapResource soapResource = mapper.map(soapResourceFile, SoapResource.class);
                result.add(soapResource);
            }
        }
        return result;
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        Iterator<SoapResourceFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            SoapResourceFile resource = iterator.next();
            if(resource.getProjectId().equals(projectId)){
                delete(resource.getId());
            }
        }
    }

    @Override
    public List<SoapResource> findWithProjectId(String projectId) {
        final List<SoapResource> resources = new ArrayList<>();
        for(SoapResourceFile resourceFile : this.collection.values()){
            if(resourceFile.getProjectId().equals(projectId)){
                SoapResource resource = this.mapper.map(resourceFile, SoapResource.class);
                resources.add(resource);
            }
        }
        return resources;
    }

    /**
     * The method loads a resource that matching the search criteria and returns the result
     *
     * @param soapResourceId The id of the resource that will be loaded
     * @return Returns the loaded resource and returns it as a String.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapResource
     * @since 1.16
     */
    @Override
    public String loadSoapResource(String soapResourceId) {
        Preconditions.checkNotNull(soapResourceId, "Resource id cannot be null");
        final SoapResourceFile soapResource = this.collection.get(soapResourceId);
        String path = this.fileDirectory + File.separator;

        if(SoapResourceType.WSDL.equals(soapResource.getType())){
            path += WSDL_DIRECTORY;
        } else if(SoapResourceType.WSDL.equals(soapResource.getType())){
            path += SCHEMA_DIRECTORY;
        }

        String resource = fileRepositorySupport.load(path, soapResource.getId() +
                this.fileExtension);
        return resource;
    }

    /**
     * The method adds a new {@link SoapResource}.
     *
     * @param soapResource The  instance of {@link SoapResource} that will be saved.
     * @param resource        The raw resource
     * @return The saved {@link SoapResource}
     * @see SoapResource
     */
    @Override
    public SoapResource saveSoapResource(SoapResource soapResource, String resource) {
        SoapResourceFile resourceFile = mapper.map(soapResource, SoapResourceFile.class);
        SoapResource saveSoapResource = save(soapResource);
        String path = this.fileDirectory + File.separator;

        if(SoapResourceType.WSDL.equals(resourceFile.getType())){
            path += WSDL_DIRECTORY;
        } else if(SoapResourceType.WSDL.equals(resourceFile.getType())){
            path
                    += SCHEMA_DIRECTORY;
        }
        try {
            this.fileRepositorySupport.save(path, saveSoapResource.getId() + this.fileExtension, resource);
        } catch (Exception e){
            LOGGER.error("Unable to upload SOAP resource", e);
        }

        return saveSoapResource;
    }

    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    @Override
    public SoapResource delete(final String id) {
        Preconditions.checkNotNull(id, "Resource id cannot be null");

        SoapResourceFile soapResource = this.collection.remove(id);

        if(soapResource != null){
            String path = this.fileDirectory + File.separator;

            if(SoapResourceType.WSDL.equals(soapResource.getType())){
                path += WSDL_DIRECTORY;
            } else if(SoapResourceType.WSDL.equals(soapResource.getType())){
                path += SCHEMA_DIRECTORY;
            }

            try {
                // Try to delete a SOAP resource.
                // This operation can fail and that is expected.
                // The reason for this is if you import a project,
                // it might not have all the resource included.
                // If that is the case, the we should only log
                // that we weren't able to delete the resource file.
                this.fileRepositorySupport.delete(path, soapResource.getId() + this.fileExtension);
            } catch (IllegalStateException e){
                LOGGER.warn("Unable to delete the following SOAP resource: " + id);
            }

        }
        return soapResource != null ? mapper.map(soapResource, SoapResource.class) : null;
    }


    /**
     * The method returns a list of {@link SoapResource} that matches the
     * search criteria.
     *
     * @param soapProjectId The id of the project.
     * @param types          The types of {@link SoapResource} that should be returned.
     * @return A list of {@link SoapResource} of the specific provided type.
     * All resources will be returned if the type is null.
     * @since 1.16
     */
    @Override
    public Collection<SoapResource> findSoapResources(final String soapProjectId, final SoapResourceType... types) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");

        final List<SoapResource> soapResources = new ArrayList<>();
        for(SoapResourceFile soapResourceFile : this.collection.values()){
            if(soapResourceFile.getProjectId().equals(soapProjectId)){
                for(SoapResourceType type : types){
                    if(type.equals(soapResourceFile.getType())){
                        SoapResource soapResource = mapper.map(soapResourceFile, SoapResource.class);
                        soapResources.add(soapResource);
                    }
                }
            }
        }
        return soapResources;
    }

    /**
     * Retrieve the {@link SoapProject} id
     * for the {@link SoapResource} with the provided id.
     *
     * @param portId The id of the {@link SoapResource}.
     * @return The id of the project.
     * @since 1.20
     */
    @Override
    public String getProjectId(String portId) {
        final SoapResourceFile resourceFile = this.collection.get(portId);

        if(resourceFile == null){
            throw new IllegalArgumentException("Unable to find a resource with the following id: " + portId);
        }
        return resourceFile.getProjectId();
    }

    @XmlRootElement(name = "soapResource")
    protected static class SoapResourceFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("projectId")
        private String projectId;
        @Mapping("type")
        private SoapResourceType type;

        @XmlElement
        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public SoapResourceType getType() {
            return type;
        }

        public void setType(SoapResourceType type) {
            this.type = type;
        }

        @XmlElement
        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
    }

}
