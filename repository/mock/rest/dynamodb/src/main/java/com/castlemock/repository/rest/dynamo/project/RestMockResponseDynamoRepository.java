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

package com.castlemock.repository.rest.dynamo.project;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Tiago Santos
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class RestMockResponseDynamoRepository extends DynamoRepository<RestMockResponseDynamoRepository.RestMockResponseDocument, RestMockResponse, String> implements RestMockResponseRepository {

    @Autowired
    public RestMockResponseDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public RestMockResponseDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    @Override
    protected RestMockResponseDocument mapToEntity(RestMockResponse dto) {
        RestMockResponseDocument entity = super.mapToEntity(dto);
        entity.nameLower = Optional.ofNullable(entity.name).map(String::toLowerCase).orElse(null);
        return entity;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(RestMockResponseDocument type) {

    }

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
        //FIXME how to deal with this...
//        final String parameterQueries = "parameterQueries";
//        mongoOperations.updateMulti(query(where(parameterQueries).is(null)),
//                BasicUpdate.update(parameterQueries, Collections.emptyList()),
//                RestMockResponseDocument.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMockResponse> search(SearchQuery query) {
        List<RestMockResponseDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("nameLower", query.getQuery().toLowerCase(), ComparisonOperator.CONTAINS));
        return toDtoList(responses);
    }

    /**
     * Delete all {@link RestMockResponseDocument} that matches the provided
     * <code>methodId</code>.
     *
     * @param methodId The id of the method.
     */
    @Override
    public void deleteWithMethodId(String methodId) {
        List<RestMockResponseDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("methodId", methodId, ComparisonOperator.EQ));
        dynamoDBMapper.batchDelete(responses);
    }

    /**
     * Find all {@link RestMockResponse} that matches the provided
     * <code>methodId</code>.
     *
     * @param methodId The id of the method.
     * @return A list of {@link RestMockResponse}.
     */
    @Override
    public List<RestMockResponse> findWithMethodId(String methodId) {
        List<RestMockResponseDocument> responses =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("methodId", methodId, ComparisonOperator.EQ));
        return toDtoList(responses);
    }

    /**
     * Retrieve the {@link RestMethod} id
     * for the {@link RestMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link RestMockResponse}.
     * @return The id of the method.
     */
    @Override
    public String getMethodId(String mockResponseId) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, mockResponseId)).orElseThrow(
                () -> new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId)
        ).getMethodId();
    }


    //@Document(collection = "restMockResponse")
    @DynamoDBTable(tableName = "restMockResponse")
    @Getter
    @Setter
    public static class RestMockResponseDocument implements Saveable<String> {

        @DynamoDBHashKey(attributeName = "id")
        @DynamoDBAutoGeneratedKey
        @Mapping("id")
        private String id;
        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "body")
        @Mapping("body")
        private String body;
        @DynamoDBAttribute(attributeName = "methodId")
        @Mapping("methodId")
        private String methodId;
        @DynamoDBAttribute(attributeName = "status")
        @DynamoDBTypeConvertedEnum
        @Mapping("status")
        private RestMockResponseStatus status;
        @DynamoDBAttribute(attributeName = "httpStatusCode")
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @DynamoDBAttribute(attributeName = "usingExpressions")
        @Mapping("usingExpressions")
        private boolean usingExpressions;
        @DynamoDBAttribute(attributeName = "httpHeaders")
        @Mapping("httpHeaders")
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        @DynamoDBAttribute(attributeName = "contentEncodings")
        @Mapping("contentEncodings")
        //FIXME list of enum
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        @DynamoDBAttribute(attributeName = "parameterQueries")
        @Mapping("parameterQueries")
        private List<RestParameterQueryDocument> parameterQueries = new CopyOnWriteArrayList<RestParameterQueryDocument>();
        @DynamoDBAttribute(attributeName = "xpathExpressions")
        @Mapping("xpathExpressions")
        private List<RestXPathExpressionDocument> xpathExpressions = new CopyOnWriteArrayList<RestXPathExpressionDocument>();
        @DynamoDBAttribute(attributeName = "jsonPathExpressions")
        @Mapping("jsonPathExpressions")
        private List<RestJsonPathExpressionDocument> jsonPathExpressions = new CopyOnWriteArrayList<RestJsonPathExpressionDocument>();
        @DynamoDBAttribute(attributeName = "headerQueries")
        @Mapping("headerQueries")
        private List<RestHeaderQueryDocument> headerQueries = new CopyOnWriteArrayList<RestHeaderQueryDocument>();

        @DynamoDBAttribute(attributeName = "nameLower")
        private String nameLower;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof RestMockResponseDocument))
                return false;

            RestMockResponseDocument that = (RestMockResponseDocument) o;

            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    //@Document(collection = "restParameterQuery")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class RestParameterQueryDocument {

        private String parameter;
        private String query;
        private boolean matchCase;
        private boolean matchAny;
        private boolean matchRegex;
    }

    //@Document(collection = "restHeaderQuery")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class RestHeaderQueryDocument {

        private String header;
        private String query;
        private boolean matchCase;
        private boolean matchAny;
        private boolean matchRegex;
    }

    @Getter
    @Setter
    public static class RestXPathExpressionDocument {

        private String expression;
    }

    @Getter
    @Setter
    protected static class RestJsonPathExpressionDocument {

        private String expression;
    }
}
