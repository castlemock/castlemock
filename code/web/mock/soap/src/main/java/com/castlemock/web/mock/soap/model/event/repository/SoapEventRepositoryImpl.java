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

package com.castlemock.web.mock.soap.model.event.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving soap events from the file system. Each soap event is stored as
 * a separate file. The class also contains the directory and the filename extension for the soap event.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapEventRepositoryImpl
 * @see RepositoryImpl
 * @see SoapEvent
 */
@Repository
public class SoapEventRepositoryImpl extends RepositoryImpl<SoapEvent, SoapEventDto, String> implements SoapEventRepository {

    @Value(value = "${soap.event.file.directory}")
    private String soapEventFileDirectory;
    @Value(value = "${soap.event.file.extension}")
    private String soapEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return soapEventFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return soapEventFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param soapEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see SoapEvent
     */
    @Override
    protected void checkType(final SoapEvent soapEvent) {
        Preconditions.checkNotNull(soapEvent, "Event cannot be null");
        Preconditions.checkNotNull(soapEvent.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(soapEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(soapEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The events for a specific operation id
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    @Override
    public List<SoapEventDto> findEventsByOperationId(String operationId) {
        final List<SoapEvent> events = new ArrayList<SoapEvent>();
        for(SoapEvent event : collection.values()){
            if(event.getOperationId().equals(operationId)){
                events.add(event);
            }
        }
        return toDtoList(events, SoapEventDto.class);
    }

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    @Override
    public SoapEventDto getOldestEvent() {
        Event oldestEvent = null;
        for(Event event : collection.values()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
                oldestEvent = event;
            }
        }

        return oldestEvent == null ? null : mapper.map(oldestEvent, SoapEventDto.class);
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
    public synchronized SoapEventDto deleteOldestEvent(){
        SoapEventDto eventDto = getOldestEvent();
        delete(eventDto.getId());
        return eventDto;
    }
}
