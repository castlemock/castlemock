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


package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class SoapPortMongoRepository extends MongoRepository<SoapPortMongoRepository.SoapPortDocument, SoapPort, String> implements SoapPortRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     */
    @Override
    protected void checkType(SoapPortDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapPort> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<SoapPortDocument> operations =
                mongoOperations.find(nameQuery, SoapPortDocument.class);
        return toDtoList(operations, SoapPort.class);
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        mongoOperations.remove(getProjectIdQuery(projectId), SoapPortDocument.class);
    }

    @Override
    public List<SoapPort> findWithProjectId(String projectId) {
        List<SoapPortDocument> responses =
                mongoOperations.find(getProjectIdQuery(projectId), SoapPortDocument.class);
        return toDtoList(responses, SoapPort.class);
    }

    /**
     * The method finds a {@link SoapPort} with the provided name
     *
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPort findWithName(final String projectId, final String soapPortName) {
        Query portIdAndNameQuery = query(where("projectId").is(projectId).and("name").is(soapPortName));
        SoapPortDocument operation =
                mongoOperations.findOne(portIdAndNameQuery, SoapPortDocument.class);
        return operation == null ? null : mapper.map(operation, SoapPort.class);
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
        Query portIdAndNameQuery = query(where("projectId").is(projectId).and("uri").is(uri));
        SoapPortDocument operation =
                mongoOperations.findOne(portIdAndNameQuery, SoapPortDocument.class);
        return operation == null ? null : mapper.map(operation, SoapPort.class);
    }

    /**
     * Retrieve the {@link SoapProject} id
     * for the {@link SoapPort} with the provided id.
     *
     * @param portId The id of the {@link SoapPort}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String portId) {
        SoapPortDocument soapPort = mongoOperations.findById(portId, SoapPortDocument.class);

        if (soapPort == null) {
            throw new IllegalArgumentException("Unable to find a port with the following id: " + portId);
        }
        return soapPort.getProjectId();
    }

    @Document(collection = "soapPort")
    protected static class SoapPortDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("uri")
        private String uri;
        @Mapping("projectId")
        private String projectId;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
    }

    private Query getProjectIdQuery(String projectId) {
        return query(getProjectIdCriteria(projectId));
    }

    private Criteria getProjectIdCriteria(String projectId) {
        return where("projectId").is(projectId);
    }
}
