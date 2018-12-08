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

package com.castlemock.repository.rest.mongo.project;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.project.AbstractProjectMongoRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The class is an implementation of mongo repository and provides the functionality to interact with mongodb.
 * The repository is responsible for loading and saving REST project from mongodb.
 *
 * @author Mohammad Hewedy
 * @see RestProjectRepository
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class RestProjectMongoRepository extends AbstractProjectMongoRepository<RestProjectMongoRepository.RestProjectDocument, RestProject> implements RestProjectRepository {

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     * <p>
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     *
     * @see #initialize
     */
    @Override
    protected void postInitiate() {

    }


    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param restProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                    mongodb.
     * @see #save
     */
    @Override
    protected void checkType(RestProjectDocument restProject) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestProject> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<RestProjectDocument> projects =
                mongoOperations.find(nameQuery, RestProjectDocument.class);
        return toDtoList(projects, RestProject.class);
    }

    /**
     * Finds a {@link RestProject} with a provided REST project name.
     *
     * @param restProjectName The name of the REST project that will be retrieved.
     * @return A {@link RestProject} that matches the provided name.
     * @see RestProject
     */
    @Override
    public RestProject findRestProjectWithName(final String restProjectName) {
        Preconditions.checkNotNull(restProjectName, "Project name cannot be null");
        Preconditions.checkArgument(!restProjectName.isEmpty(), "Project name cannot be empty");

        Query exactNameIgnoreCaseQuery = query(where("name").regex("^" + restProjectName + "$", "i"));
        RestProjectDocument project = mongoOperations.findOne(exactNameIgnoreCaseQuery, RestProjectDocument.class);
        return project == null ? null : mapper.map(project, RestProject.class);
    }

    @Document(collection = "restProject")
    protected static class RestProjectDocument extends AbstractProjectMongoRepository.ProjectDocument {

    }
}
