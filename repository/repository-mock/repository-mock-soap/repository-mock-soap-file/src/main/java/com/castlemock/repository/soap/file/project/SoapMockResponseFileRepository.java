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

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.SearchValidator;
import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import com.castlemock.repository.soap.file.project.converter.SoapMockResponseConverter;
import com.castlemock.repository.soap.file.project.converter.SoapMockResponseFileConverter;
import com.castlemock.repository.soap.file.project.model.SoapMockResponseFile;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
@Profile(Profiles.FILE)
public class SoapMockResponseFileRepository extends FileRepository<SoapMockResponseFile, SoapMockResponse, String> implements SoapMockResponseRepository {

    @Value(value = "${soap.response.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.response.file.extension}")
    private String fileExtension;

    public SoapMockResponseFileRepository() {
        super(SoapMockResponseFileConverter::toSoapMockResponse, SoapMockResponseConverter::toSoapMockResponseFile);
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
    protected void checkType(final SoapMockResponseFile type) {

    }


    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @since 1.4
     */
    @Override
    protected void postInitiate() {
        for(SoapMockResponseFile soapMockResponse : collection.values()) {
            List<HttpHeaderFile> httpHeaders = new CopyOnWriteArrayList<>();
            if (soapMockResponse.getHttpHeaders() != null) {
                httpHeaders.addAll(soapMockResponse.getHttpHeaders());
            }
            soapMockResponse.setHttpHeaders(httpHeaders);

            List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<>();
            if (soapMockResponse.getContentEncodings() != null) {
                contentEncodings.addAll(soapMockResponse.getContentEncodings());
            }

            soapMockResponse.setContentEncodings(contentEncodings);
            save(soapMockResponse);
        }
    }


    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapMockResponse> search(final SearchQuery query) {
        return this.collection.values()
                .stream()
                .filter(mockResponse -> SearchValidator.validate(mockResponse.getName(), query.getQuery()))
                .map(SoapMockResponseFileConverter::toSoapMockResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWithOperationId(final String operationId) {
        this.collection.values()
                .stream()
                .filter(mockResponse -> mockResponse.getOperationId().equals(operationId))
                .map(SoapMockResponseFile::getId)
                .toList()
                .forEach(this::delete);
    }

    @Override
    public List<SoapMockResponse> findWithOperationId(final String operationId) {
        return this.collection.values()
                .stream()
                .filter(mockResponse -> mockResponse.getOperationId().equals(operationId))
                .map(SoapMockResponseFileConverter::toSoapMockResponse)
                .toList();
    }

    /**
     * Retrieve the {@link SoapOperation} id
     * for the {@link SoapMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link SoapMockResponse}.
     * @return The id of the operation.
     * @since 1.20
     */
    @Override
    public String getOperationId(final String mockResponseId) {
        final SoapMockResponseFile mockResponse = this.collection.get(mockResponseId);

        if(mockResponse == null){
            throw new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId);
        }
        return mockResponse.getOperationId();
    }

}
