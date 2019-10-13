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

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class SoapResourceMongoRepository extends MongoRepository<SoapResourceMongoRepository.SoapResourceDocument, SoapResource, String> implements SoapResourceRepository {

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
    protected void checkType(SoapResourceDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapResource> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<SoapResourceDocument> resources =
                mongoOperations.find(nameQuery, SoapResourceDocument.class);
        return toDtoList(resources, SoapResource.class);
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        mongoOperations.remove(getProjectIdQuery(projectId), SoapResourceDocument.class);
    }

    @Override
    public List<SoapResource> findWithProjectId(String projectId) {
        List<SoapResourceDocument> resources =
                mongoOperations.find(getProjectIdQuery(projectId), SoapResourceDocument.class);
        return toDtoList(resources, SoapResource.class);
    }

    /**
     * The method loads a resource that matching the search criteria and returns the result
     *
     * @param soapResourceId The id of the resource that will be loaded
     * @return Returns the loaded resource and returns it as a String.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapResource
     */
    @Override
    public String loadSoapResource(String soapResourceId) {
        Preconditions.checkNotNull(soapResourceId, "Resource id cannot be null");

        SoapResourceDocument soapResource =
                mongoOperations.findById(soapResourceId, SoapResourceDocument.class);
        if (soapResource == null) {
            throw new IllegalArgumentException("Unable to find a SOAP resource with id " + soapResourceId);
        }
        return soapResource.getContent();
    }

    /**
     * The method adds a new {@link SoapResource}.
     *
     * @param soapResource The  instance of {@link SoapResource} that will be saved.
     * @param resource     The raw resource
     * @return The saved {@link SoapResource}
     * @see SoapResource
     */
    @Override
    public SoapResource saveSoapResource(SoapResource soapResource, String resource) {
        soapResource.setContent(resource);
        return save(soapResource);
    }

    /**
     * The method returns a list of {@link SoapResource} that matches the
     * search criteria.
     *
     * @param soapProjectId The id of the project.
     * @param types         The types of {@link SoapResource} that should be returned.
     * @return A list of {@link SoapResource} of the specific provided type.
     * All resources will be returned if the type is null.
     */
    @Override
    public Collection<SoapResource> findSoapResources(final String soapProjectId, final SoapResourceType... types) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");

        List<SoapResourceDocument> resources =
                mongoOperations.find(getProjectIdQuery(soapProjectId), SoapResourceDocument.class);

        final List<SoapResource> soapResources = new ArrayList<>();
        for (SoapResourceDocument soapResourceDocument : resources) {
            for (SoapResourceType type : types) {
                if (type.equals(soapResourceDocument.getType())) {
                    SoapResource soapResource = mapper.map(soapResourceDocument, SoapResource.class);
                    soapResources.add(soapResource);
                }
            }
        }
        return soapResources;
    }

    /**
     * Retrieve the {@link SoapProject} id
     * for the {@link SoapResource} with the provided id.
     *
     * @param resourceId The id of the {@link SoapResource}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String resourceId) {
        final SoapResourceDocument resource =
                mongoOperations.findById(resourceId, SoapResourceDocument.class);

        if (resource == null) {
            throw new IllegalArgumentException("Unable to find a resource with the following id: " + resourceId);
        }
        return resource.getProjectId();
    }

    @Document(collection = "soapResource")
    protected static class SoapResourceDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("projectId")
        private String projectId;
        @Mapping("type")
        private SoapResourceType type;
        @Mapping("content")
        private String content;

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

        public SoapResourceType getType() {
            return type;
        }

        public void setType(SoapResourceType type) {
            this.type = type;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    private Query getProjectIdQuery(String projectId) {
        return query(getProjectIdCriteria(projectId));
    }

    private Criteria getProjectIdCriteria(String projectId) {
        return where("projectId").is(projectId);
    }
}
