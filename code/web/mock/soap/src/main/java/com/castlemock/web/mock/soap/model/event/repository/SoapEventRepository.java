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


import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;

import java.util.List;

/**
 * The soap event file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and soap events to the file system. Each
 * soap event is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapEvent
 * @see Repository
 */
public interface SoapEventRepository extends Repository<SoapEvent, SoapEventDto, String> {

    /**
     * The events for a specific operation id
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    List<SoapEventDto> findEventsByOperationId(String operationId);

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    SoapEventDto getOldestEvent();

    /**
     * The method finds and deletes the oldest event.
     * @return The event that was deleted.
     * @since 1.5
     */
    SoapEventDto deleteOldestEvent();

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    void clearAll();
}
