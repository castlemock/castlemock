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

package com.castlemock.web.mock.graphql.model.event.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLEvent;
import com.castlemock.core.mock.graphql.model.event.dto.GraphQLEventDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class GraphQLEventRepositoryImpl extends RepositoryImpl<GraphQLEvent, GraphQLEventDto, String> implements GraphQLEventRepository {

    @Value(value = "${graphql.event.file.directory}")
    private String graphQLEventFileDirectory;
    @Value(value = "${graphql.event.file.extension}")
    private String graphQLEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return graphQLEventFileDirectory;
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
        return graphQLEventFileExtension;
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
    protected void checkType(GraphQLEvent type) {
        Preconditions.checkNotNull(type, "Event cannot be null");
        Preconditions.checkNotNull(type.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(type.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(type.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The events for a specific operation id
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    @Override
    public List<GraphQLEventDto> findEventsByOperationId(String operationId) {
        final List<GraphQLEvent> events = new ArrayList<GraphQLEvent>();
        for(GraphQLEvent event : collection.values()){
            if(event.getOperationId().equals(operationId)){
                events.add(event);
            }
        }
        return toDtoList(events, GraphQLEventDto.class);
    }

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    @Override
    public GraphQLEventDto getOldestEvent() {
        Event oldestEvent = null;
        for(Event event : collection.values()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
                oldestEvent = event;
            }
        }

        return oldestEvent == null ? null : mapper.map(oldestEvent, GraphQLEventDto.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     * @return The event that was deleted.
     * @since 1.5
     */
    @Override
    public synchronized GraphQLEventDto deleteOldestEvent(){
        GraphQLEventDto eventDto = getOldestEvent();
        delete(eventDto.getId());
        return eventDto;
    }

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        Iterator<GraphQLEvent> iterator = collection.values().iterator();
        while(iterator.hasNext()){
            GraphQLEvent soapEvent = iterator.next();
            delete(soapEvent.getId());
        }
    }
}
