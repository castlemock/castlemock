/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.core.file.event.converter.EventFileConverter;
import com.castlemock.repository.rest.file.event.model.RestEventFile;

import java.util.Optional;

public final class RestEventFileConverter {

    private RestEventFileConverter() {

    }

    public static RestEvent toRestEvent(final RestEventFile restEventFile) {
        return EventFileConverter.toEvent(restEventFile, RestEvent.builder())
                .methodId(restEventFile.getMethodId())
                .projectId(restEventFile.getProjectId())
                .applicationId(restEventFile.getApplicationId())
                .resourceId(restEventFile.getResourceId())
                .request(RestRequestFileConverter.toRestRequest(restEventFile.getRequest()))
                .response(Optional.ofNullable(restEventFile.getResponse())
                        .map(RestResponseFileConverter::toRestResponse)
                        .orElse(null))
                .build();
    }


}
