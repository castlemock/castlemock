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

import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.soap.file.project.converter.SoapPortConverter;
import com.castlemock.repository.soap.file.project.converter.SoapPortFileConverter;
import com.castlemock.repository.soap.file.project.model.SoapPortFile;
import com.castlemock.repository.soap.project.SoapPortRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile(Profiles.FILE)
public class SoapPortFileRepository extends FileRepository<SoapPortFile, SoapPort, String> implements SoapPortRepository {

    @Value(value = "${soap.port.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.port.file.extension}")
    private String fileExtension;

    public SoapPortFileRepository() {
        super(SoapPortFileConverter::toSoapPortFile, SoapPortConverter::toSoapPort);
    }

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
    protected void checkType(final SoapPortFile type) {

    }

    @Override
    public void deleteWithProjectId(final String projectId) {
        this.collection.values()
                .stream()
                .filter(port -> port.getProjectId().equals(projectId))
                .map(SoapPortFile::getId)
                .toList()
                .forEach(this::delete);
    }

    @Override
    public List<SoapPort> findWithProjectId(final String projectId) {
        return this.collection.values()
                .stream()
                .filter(port -> port.getProjectId().equals(projectId))
                .map(SoapPortFileConverter::toSoapPortFile)
                .toList();
    }

    /**
     * The method finds a {@link SoapPort} with the provided name
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public Optional<SoapPort> findWithName(final String projectId, final String soapPortName) {
        return this.collection.values()
                .stream()
                .filter(port -> port.getProjectId().equals(projectId))
                .filter(port -> port.getName().equals(soapPortName))
                .findFirst()
                .map(SoapPortFileConverter::toSoapPortFile);
    }

    /**
     * The method finds a {@link SoapPort} with the provided uri
     *
     * @param uri       The uri used by the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public Optional<SoapPort> findWithUri(final String projectId, final String uri) {
        return this.collection.values()
                .stream()
                .filter(port -> port.getProjectId().equals(projectId))
                .filter(port -> port.getUri().equals(uri))
                .findFirst()
                .map(SoapPortFileConverter::toSoapPortFile);
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
    public String getProjectId(final String portId) {
        final SoapPortFile portFile = this.collection.get(portId);

        if(portFile == null){
            throw new IllegalArgumentException("Unable to find a port with the following id: " + portId);
        }
        return portFile.getProjectId();
    }



}
