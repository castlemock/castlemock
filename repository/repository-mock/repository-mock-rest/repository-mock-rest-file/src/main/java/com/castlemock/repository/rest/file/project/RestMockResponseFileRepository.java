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

package com.castlemock.repository.rest.file.project;

import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.rest.file.project.converter.RestMockResponseConverter;
import com.castlemock.repository.rest.file.project.converter.RestMockResponseFileConverter;
import com.castlemock.repository.rest.file.project.model.RestMockResponseFile;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile(Profiles.FILE)
public class RestMockResponseFileRepository extends FileRepository<RestMockResponseFile, RestMockResponse, String> implements RestMockResponseRepository {

    @Value(value = "${rest.response.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.response.file.extension}")
    private String fileExtension;


    public RestMockResponseFileRepository() {
        super(RestMockResponseFileConverter::toRestMockResponse, RestMockResponseConverter::toRestMockResponse);
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
    protected void checkType(RestMockResponseFile type) {

    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     * <p>
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @since 1.4
     */
    @Override
    protected void postInitiate() {

    }

    /**
     * Find all {@link RestMockResponse} that matches the provided
     * <code>methodId</code>.
     *
     * @param methodId The id of the method.
     * @return A list of {@link RestMockResponse}.
     */
    @Override
    public List<RestMockResponse> findWithMethodId(final String methodId) {
        return this.collection.values()
                .stream()
                .filter(response -> response.getMethodId().equals(methodId))
                .map(RestMockResponseFileConverter::toRestMockResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve the {@link RestMethod} id
     * for the {@link RestMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link RestMockResponse}.
     * @return The id of the method.
     * @since 1.20
     */
    @Override
    public String getMethodId(String mockResponseId) {
        final RestMockResponseFile mockResponseFile = this.collection.get(mockResponseId);

        if(mockResponseFile == null){
            throw new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId);
        }
        return mockResponseFile.getMethodId();
    }

}
