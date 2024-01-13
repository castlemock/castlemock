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

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.SearchValidator;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.rest.file.project.converter.RestMethodConverter;
import com.castlemock.repository.rest.file.project.converter.RestMethodFileConverter;
import com.castlemock.repository.rest.file.project.model.RestMethodFile;
import com.castlemock.repository.rest.project.RestMethodRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile(Profiles.FILE)
public class RestMethodFileRepository extends FileRepository<RestMethodFile, RestMethod, String> implements RestMethodRepository {

    @Value(value = "${rest.method.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.method.file.extension}")
    private String fileExtension;


    public RestMethodFileRepository() {
        super(RestMethodFileConverter::toRestMethod, RestMethodConverter::toRestMethod);
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
    protected void checkType(final RestMethodFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMethod> search(final SearchQuery query) {
        return collection.values()
                .stream()
                .filter(method -> SearchValidator.validate(method.getName(), query.getQuery()))
                .map(RestMethodFileConverter::toRestMethod)
                .toList();
    }


    /**
     * Delete all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     */
    @Override
    public void deleteWithResourceId(final String resourceId) {
        this.collection.values()
                .stream()
                .filter(method -> method.getResourceId().equals(resourceId))
                .map(RestMethodFile::getId)
                .toList()
                .forEach(this::delete);
    }

    /**
     * Find all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod}.
     * @since 1.20
     */
    @Override
    public List<RestMethod> findWithResourceId(final String resourceId) {
        return this.collection.values()
                .stream()
                .filter(method -> method.getResourceId().equals(resourceId))
                .map(RestMethodFileConverter::toRestMethod)
                .collect(Collectors.toList());
    }

    /**
     * Find all {@link RestMethod} ids that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithResourceId(final String resourceId) {
        final List<String> ids = new ArrayList<>();
        for(RestMethodFile methodFile : this.collection.values()){
            if(methodFile.getResourceId().equals(resourceId)){
                ids.add(methodFile.getId());
            }
        }
        return ids;
    }

    /**
     * Retrieve the {@link RestResource} id
     * for the {@link RestMethod} with the provided id.
     *
     * @param methodId The id of the {@link RestMethod}.
     * @return The id of the resource.
     * @since 1.20
     */
    @Override
    public String getResourceId(final String methodId) {
        final RestMethodFile methodFile = this.collection.get(methodId);

        if(methodFile == null){
            throw new IllegalArgumentException("Unable to find a method with the following id: " + methodId);
        }
        return methodFile.getResourceId();
    }


}
