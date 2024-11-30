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

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.soap.file.project.converter.SoapOperationConverter;
import com.castlemock.repository.soap.file.project.converter.SoapOperationFileConverter;
import com.castlemock.repository.soap.file.project.model.SoapOperationFile;
import com.castlemock.repository.soap.file.project.model.SoapOperationIdentifierFile;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile(Profiles.FILE)
public class SoapOperationFileRepository extends FileRepository<SoapOperationFile, SoapOperation, String> implements SoapOperationRepository {

    @Value(value = "${soap.operation.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.operation.file.extension}")
    private String fileExtension;


    public SoapOperationFileRepository() {
        super(SoapOperationFileConverter::toSoapOperation, SoapOperationConverter::toSoapOperationFile);
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
    protected void checkType(final SoapOperationFile type) {

    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method does not contain any functionality and the
     * whole idea is the it should be overridden by subclasses, but only if certain functionality is required to
     * run after the {@link #initialize} method has completed.
     * @see #initialize
     */
    @Override
    public void postInitiate(){

    }

    @Override
    public void deleteWithPortId(final String portId) {
        this.collection.values()
                .stream()
                .filter(operation -> operation.getPortId().equals(portId))
                .map(SoapOperationFile::getId)
                .toList()
                .forEach(this::delete);
    }


    @Override
    public List<SoapOperation> findWithPortId(final String portId) {
        return this.collection.values()
                .stream()
                .filter(operation -> operation.getPortId().equals(portId))
                .map(SoapOperationFileConverter::toSoapOperation)
                .toList();
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public Optional<SoapOperation> findWithName(final String soapPortId,
                                               final String soapOperationName){
        return this.collection.values()
                .stream()
                .filter(operation -> operation.getPortId().equals(soapPortId))
                .filter(operation -> operation.getName().equals(soapOperationName))
                .findFirst()
                .map(SoapOperationFileConverter::toSoapOperation);
    }

    /**
     * Find a {@link SoapOperation} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     *
     * @param method     The HTTP method
     * @param version    The SOAP version
     * @param operationIdentifier The identifier
     * @return A {@link SoapOperation} that matches the provided search criteria.
     */
    @Override
    public Optional<SoapOperation> findWithMethodAndVersionAndIdentifier(final String portId, final HttpMethod method,
                                                               final SoapVersion version,
                                                               final SoapOperationIdentifier operationIdentifier) {
        for(SoapOperationFile soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(portId) &&
                    soapOperation.getHttpMethod().equals(method) &&
                    soapOperation.getSoapVersion().equals(version)){

                final SoapOperationIdentifierFile operationIdentifierFile =
                        soapOperation.getOperationIdentifier();

                if(operationIdentifier.getName().equalsIgnoreCase(operationIdentifierFile.getName())){

                    // Three ways to identify SOAP operation:
                    // 1. Namespace is missing from the stored files (Legacy)
                    // 2. The identify strategy is ELEMENT (Ignore namespace)
                    // 3. Both the name and namespace is matching
                    if(operationIdentifierFile.getNamespace() == null ||
                            soapOperation.getIdentifyStrategy() == SoapOperationIdentifyStrategy.ELEMENT ||
                            operationIdentifierFile.getNamespace().equalsIgnoreCase(operationIdentifier.getNamespace().orElse(null))) {
                        return Optional.of(SoapOperationFileConverter.toSoapOperation(soapOperation));
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieve the {@link SoapPort} id
     * for the {@link SoapOperation} with the provided id.
     *
     * @param operationId The id of the {@link SoapOperation}.
     * @return The id of the port.
     * @since 1.20
     */
    @Override
    public String getPortId(String operationId) {
        final SoapOperationFile operationFile = this.collection.get(operationId);

        if(operationFile == null){
            throw new IllegalArgumentException("Unable to find an operation with the following id: " + operationId);
        }
        return operationFile.getPortId();
    }






}


