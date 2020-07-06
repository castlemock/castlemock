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

package com.castlemock.repository.rest.dynamo.event;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.dynamodb.DynamoRepository;
import com.castlemock.repository.core.dynamodb.event.AbstractEventDynamoRepository;
import com.castlemock.repository.rest.event.RestEventRepository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The class is an implementation of the REST event repository and provides the functionality to interact with dynamodb.
 * The repository is responsible for loading and saving rest events from dynamodb.
 *
 * @author Tiago Santos
 * @see RestEventDynamoRepository
 * @see DynamoRepository
 * @since 1.51
 */
@Repository
@Profile(Profiles.DYNAMODB)
public class RestEventDynamoRepository extends AbstractEventDynamoRepository<RestEventDynamoRepository.RestEventDocument, RestEvent> implements RestEventRepository {

    @Autowired
    public RestEventDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB,
                                     DynamoDBMapperConfig dynamoDBMapperConfig) {
        super(mapper, amazonDynamoDB, dynamoDBMapperConfig);
    }

    public RestEventDynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        super(mapper, amazonDynamoDB);
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from
     * dynamodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param restEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                  dynamodb.
     * @see #save
     */
    @Override
    protected void checkType(final RestEventDocument restEvent) {
        Preconditions.checkNotNull(restEvent, "Event cannot be null");
        Preconditions.checkNotNull(restEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(restEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The service finds the oldest event
     *
     * @return The oldest event
     */
    @Override
    public RestEvent getOldestEvent() {
        return findOldestEventEntity().map(this::mapToDTO).orElse(null);
    }

    protected Optional<RestEventDocument> findOldestEventEntity() {
        return dynamoDBMapper.scan(entityClass, new DynamoDBScanExpression())
                .stream().min(Comparator.comparing(RestEventDocument::getStartDate));
    }

    /**
     * Find events by REST method ID
     *
     * @param restMethodId The id of the REST method
     * @return A list of {@link RestEvent} that matches the provided <code>restMethodId</code>
     */
    @Override
    public List<RestEvent> findEventsByMethodId(final String restMethodId) {
        List<RestEventDocument> events =
                dynamoDBMapper.scan(entityClass,
                        getAttributeScan("methodId", restMethodId, ComparisonOperator.EQ));
        return toDtoList(events);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestEvent> search(SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     *
     * @return The event that was deleted.
     */
    @Override
    public RestEvent deleteOldestEvent() {
        Optional<RestEventDocument> optionalOldestEvent = findOldestEventEntity();
        optionalOldestEvent.ifPresent(x -> dynamoDBMapper.delete(x));
        return optionalOldestEvent.map(this::mapToDTO).orElse(null);
    }

    /**
     * The method clears and deletes all logs.
     */
    @Override
    public void clearAll() {
        //drop vs remove => https://stackoverflow.com/q/12147783
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(entityClass);
        TableUtils.deleteTableIfExists(amazonDynamoDB, deleteTableRequest);
        createTableIfNotExists();
    }

    //@Document(collection = "restEvent")
    @DynamoDBTable(tableName = "restEvent")
    @Getter
    @Setter
    public static class RestEventDocument extends AbstractEventDynamoRepository.EventDocument {

        @DynamoDBAttribute(attributeName = "request")
        @Mapping("request")
        private RestRequestDocument request;
        @DynamoDBAttribute(attributeName = "response")
        @Mapping("response")
        private RestResponseDocument response;
        @DynamoDBAttribute(attributeName = "projectId")
        @Mapping("projectId")
        private String projectId;
        @DynamoDBAttribute(attributeName = "applicationId")
        @Mapping("applicationId")
        private String applicationId;
        @DynamoDBAttribute(attributeName = "resourceId")
        @Mapping("resourceId")
        private String resourceId;
        @DynamoDBAttribute(attributeName = "methodId")
        @Mapping("methodId")
        private String methodId;
    }

    //@Document(collection = "restRequest")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class RestRequestDocument {

        @DynamoDBAttribute(attributeName = "body")
        @Mapping("body")
        private String body;
        @DynamoDBAttribute(attributeName = "contentType")
        @Mapping("contentType")
        private String contentType;
        @DynamoDBAttribute(attributeName = "uri")
        @Mapping("uri")
        private String uri;
        @DynamoDBAttribute(attributeName = "httpMethod")
        @DynamoDBTypeConvertedEnum
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @DynamoDBAttribute(attributeName = "httpParameters")
        @Mapping("httpParameters")
        private List<HttpParameterDocument> httpParameters;
        @DynamoDBAttribute(attributeName = "httpHeaders")
        @Mapping("httpHeaders")
        private List<HttpHeaderDocument> httpHeaders;
    }

    //@Document(collection = "restResponse")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class RestResponseDocument {

        @DynamoDBAttribute(attributeName = "body")
        @Mapping("body")
        private String body;
        @DynamoDBAttribute(attributeName = "mockResponseName")
        @Mapping("mockResponseName")
        private String mockResponseName;
        @DynamoDBAttribute(attributeName = "httpStatusCode")
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @DynamoDBAttribute(attributeName = "contentType")
        @Mapping("contentType")
        private String contentType;
        @DynamoDBAttribute(attributeName = "httpHeaders")
        @Mapping("httpHeaders")
        private List<HttpHeaderDocument> httpHeaders;
        @DynamoDBAttribute(attributeName = "contentEncodings")
        @Mapping("contentEncodings")
        //FIXME list enum
        private List<ContentEncoding> contentEncodings;
    }
}
