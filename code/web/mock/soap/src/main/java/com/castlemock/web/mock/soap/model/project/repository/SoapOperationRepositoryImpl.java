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


package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class SoapOperationRepositoryImpl extends RepositoryImpl<SoapOperation, SoapOperationDto, String> implements SoapOperationRepository {

    @Value(value = "${soap.operation.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.operation.file.extension}")
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
    protected void checkType(SoapOperation type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        return null;
    }

    @Override
    public void deleteWithPortId(String portId) {
        Iterator<SoapOperation> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            SoapOperation operation = iterator.next();
            if(operation.getPortId().equals(portId)){
                delete(operation.getId());
            }
        }
    }

    @Override
    public List<SoapOperationDto> findWithPortId(String portId) {
        final List<SoapOperationDto> operations = new ArrayList<>();
        for(SoapOperation operation : this.collection.values()){
            if(operation.getPortId().equals(portId)){
                SoapOperationDto operationDto = this.mapper.map(operation, SoapOperationDto.class);
                operations.add(operationDto);
            }
        }
        return operations;
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public SoapOperationDto findWithName(final String soapPortId,
                                         final String soapOperationName){
        for(SoapOperation soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(soapPortId) &&
                    soapOperation.getName().equals(soapOperationName)){
                return mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return null;
    }

    /**
     * Find a {@link SoapOperationDto} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     *
     * @param method     The HTTP method
     * @param version    The SOAP version
     * @param identifier The identifier
     * @return A {@link SoapOperationDto} that matches the provided search criteria.
     */
    @Override
    public SoapOperationDto findWithMethodAndVersionAndIdentifier(final String portId, final HttpMethod method,
                                                                  final SoapVersion version, final String identifier) {
        for(SoapOperation soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(portId) &&
                    soapOperation.getHttpMethod().equals(method) &&
                    soapOperation.getSoapVersion().equals(version) &&
                    soapOperation.getIdentifier().equalsIgnoreCase(identifier)){
                return this.mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return null;
    }

    /**
     * Updates the current response sequence index.
     *
     * @param soapOperationId The operation id.
     * @param index           The new response sequence index.
     * @since 1.17
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String soapOperationId, final Integer index) {
        SoapOperation soapOperation = collection.get(soapOperationId);
        soapOperation.setCurrentResponseSequenceIndex(index);
    }
}
