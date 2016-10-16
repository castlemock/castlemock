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

package com.castlemock.web.mock.rest.model.event.repository;


import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;

import java.util.List;

/**
 * The rest event repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and rest events to the file system. Each
 * rest event is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestEvent
 * @see Repository
 */
public interface RestEventRepository extends Repository<RestEvent, RestEventDto, String> {

    /**
     * Find events by REST method ID
     * @param restMethodId The id of the REST method
     * @return A list of {@link RestEventDto} that matches the provided <code>restMethodId</code>
     */
    List<RestEventDto> findEventsByMethodId(String restMethodId);

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    RestEventDto getOldestEvent();

    /**
     * The method finds and deletes the oldest event.
     * @return The event that was deleted.
     * @since 1.5
     */
    RestEventDto deleteOldestEvent();

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    void clearAll();

}
