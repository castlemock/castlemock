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


package com.castlemock.web.mock.soap.repository.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.web.basis.repository.RepositoryImpl;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SoapPortRepositoryImpl extends RepositoryImpl<SoapPortRepositoryImpl.SoapPortFile, SoapPort, String> implements SoapPortRepository {

    @Value(value = "${soap.port.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.port.file.extension}")
    private String fileExtension;

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
    protected void checkType(SoapPortFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapPort> search(SearchQuery query) {
        final List<SoapPort> result = new LinkedList<SoapPort>();
        for(SoapPortFile soapPortFile : collection.values()){
            if(SearchValidator.validate(soapPortFile.getName(), query.getQuery())){
                SoapPort soapPort = mapper.map(soapPortFile, SoapPort.class);
                result.add(soapPort);
            }
        }
        return result;
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        Iterator<SoapPortFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            SoapPortFile port = iterator.next();
            if(port.getProjectId().equals(projectId)){
                delete(port.getId());
            }
        }
    }

    @Override
    public List<SoapPort> findWithProjectId(String projectId) {
        final List<SoapPort> ports = new ArrayList<>();
        for(SoapPortFile portFile : this.collection.values()){
            if(portFile.getProjectId().equals(projectId)){
                SoapPort port = this.mapper.map(portFile, SoapPort.class);
                ports.add(port);
            }
        }
        return ports;
    }

    /**
     * The method finds a {@link SoapPort} with the provided name
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPort findWithName(final String projectId, final String soapPortName) {
        for(SoapPortFile soapPort : collection.values()){
            if(soapPort.getProjectId().equals(projectId) &&
                    soapPort.getName().equals(soapPortName)){
                return mapper.map(soapPort, SoapPort.class);
            }
        }
        return null;
    }

    /**
     * The method finds a {@link SoapPort} with the provided uri
     *
     * @param projectId
     * @param uri       The uri used by the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPort findWithUri(String projectId, String uri) {
        for(SoapPortFile soapPort : collection.values()){
            if(soapPort.getProjectId().equals(projectId) &&
                    soapPort.getUri().equals(uri)){
                return mapper.map(soapPort, SoapPort.class);
            }
        }
        return null;
    }

    /**
     * Retrieve the {@link SoapProject} id
     * for the {@link SoapPort} with the provided id.
     *
     * @param portId The id of the {@link SoapPort}.
     * @return The id of the project.
     * @since 1.20
     */
    @Override
    public String getProjectId(String portId) {
        final SoapPortFile portFile = this.collection.get(portId);

        if(portFile == null){
            throw new IllegalArgumentException("Unable to find a port with the following id: " + portId);
        }
        return portFile.getProjectId();
    }

    @XmlRootElement(name = "soapPort")
    protected static class SoapPortFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("uri")
        private String uri;
        @Mapping("projectId")
        private String projectId;

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
        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
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
