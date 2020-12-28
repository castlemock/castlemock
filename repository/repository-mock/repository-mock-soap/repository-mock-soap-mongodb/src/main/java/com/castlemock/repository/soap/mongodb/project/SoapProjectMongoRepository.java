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

package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.project.AbstractProjectMongoRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The class is an implementation of the mongo repository and provides the functionality to interact with mongodb.
 * The repository is responsible for loading and saving soap project from mongodb.
 *
 * @author Mohammad Hewedy
 * @see SoapProjectRepository
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class SoapProjectMongoRepository extends AbstractProjectMongoRepository<SoapProjectMongoRepository.SoapProjectDocument, SoapProject> implements SoapProjectRepository {

    /**
     * Finds a project by a given name
     *
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    @Override
    public SoapProject findSoapProjectWithName(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");

        Query exactNameIgnoreCaseQuery = query(where("name").regex("^" + name + "$", "i"));
        SoapProjectDocument project = mongoOperations.findOne(exactNameIgnoreCaseQuery, SoapProjectDocument.class);
        return project == null ? null : mapper.map(project, SoapProject.class);
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param soapProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                    mongodb.
     * @see #save
     */
    @Override
    protected void checkType(SoapProjectDocument soapProject) {

    }

    /**
     * Delete an instance that match the provided id
     *
     * @param soapProjectId The instance that matches the provided id will be deleted in the database
     */
    @Override
    public SoapProject delete(final String soapProjectId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        final SoapProjectDocument soapProject =
                mongoOperations.findById(soapProjectId, SoapProjectDocument.class);

        if (soapProject == null) {
            throw new IllegalArgumentException("Unable to find a SOAP project with id " + soapProjectId);
        }

        return super.delete(soapProjectId);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapProject> search(final SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<SoapProjectDocument> projects =
                mongoOperations.find(nameQuery, SoapProjectDocument.class);
        return toDtoList(projects, SoapProject.class);
    }

    @Document(collection = "soapProject")
    protected static class SoapProjectDocument extends AbstractProjectMongoRepository.ProjectDocument {

    }

}
