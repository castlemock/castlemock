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
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.rest.file.project.converter.RestResourceConverter;
import com.castlemock.repository.rest.file.project.converter.RestResourceFileConverter;
import com.castlemock.repository.rest.file.project.model.RestResourceFile;
import com.castlemock.repository.rest.project.RestResourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile(Profiles.FILE)
public class RestResourceFileRepository extends FileRepository<RestResourceFile, RestResource, String> implements RestResourceRepository {

    @Value(value = "${rest.resource.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.resource.file.extension}")
    private String fileExtension;

    public RestResourceFileRepository() {
        super(RestResourceFileConverter::toRestResource, RestResourceConverter::toRestResourceFile);
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
    protected void checkType(final RestResourceFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestResource> search(final SearchQuery query) {
        return this.collection.values()
                .stream()
                .filter(resource -> SearchValidator.validate(resource.getName(), query.getQuery()))
                .map(RestResourceFileConverter::toRestResource)
                .toList();
    }

    /**
     * Delete all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     */
    @Override
    public void deleteWithApplicationId(final String applicationId) {
        this.collection.values()
                .stream()
                .filter(resource -> resource.getApplicationId().equals(applicationId))
                .map(RestResourceFile::getId)
                .toList()
                .forEach(this::delete);
    }

    /**
     * Find all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource}.
     * @since 1.20
     */
    @Override
    public List<RestResource> findWithApplicationId(final String applicationId) {
        return collection.values()
                .stream()
                .filter(resource -> resource.getApplicationId().equals(applicationId))
                .map(RestResourceFileConverter::toRestResource)
                .collect(Collectors.toList());
    }

    /**
     * Find all {@link RestResource} ids that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithApplicationId(final String applicationId) {
        return this.collection.values()
                .stream()
                .filter(resource -> resource.getApplicationId().equals(applicationId))
                .map(RestResourceFile::getId)
                .collect(Collectors.toList());
    }

    /**
     * Finds a {@link RestResource} with a URI
     *
     * @param applicationId The id of the {@link RestApplication}
     * @param resourceUri   The URI of a {@link RestResource}
     * @return A {@link RestResource} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     */
    @Override
    public Optional<RestResource> findRestResourceByUri(final String applicationId, final String resourceUri) {
        return this.collection.values()
                .stream()
                .filter(resource -> resource.getApplicationId().equals(applicationId) &&
                        resourceUri.equals(resource.getUri()))
                .map(RestResourceFileConverter::toRestResource)
                .findFirst();
    }

    /**
     * Retrieve the {@link RestApplication} id
     * for the {@link RestResource} with the provided id.
     *
     * @param resourceId The id of the {@link RestResource}.
     * @return The id of the application.
     * @since 1.20
     */
    @Override
    public Optional<String> getApplicationId(final String resourceId) {
        return Optional.ofNullable(this.collection.get(resourceId))
                .map(RestResourceFile::getApplicationId);
    }

}
