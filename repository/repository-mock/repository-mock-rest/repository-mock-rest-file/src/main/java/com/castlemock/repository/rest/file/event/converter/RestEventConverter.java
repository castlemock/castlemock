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
import com.castlemock.repository.core.file.event.converter.EventConverter;
import com.castlemock.repository.rest.file.event.model.RestEventFile;

public final class RestEventConverter {

    private RestEventConverter() {

    }

    public static RestEventFile toRestEventFile(final RestEvent restEvent) {
        return EventConverter.toEventFile(restEvent, RestEventFile.builder())
                .methodId(restEvent.getMethodId())
                .projectId(restEvent.getProjectId())
                .applicationId(restEvent.getApplicationId())
                .resourceId(restEvent.getResourceId())
                .request(RestRequestConverter.toRestRequestFile(restEvent.getRequest()))
                .response(restEvent.getResponse()
                        .map(RestResponseConverter::toRestResponseFile)
                        .orElse(null))
                .build();
    }


}
