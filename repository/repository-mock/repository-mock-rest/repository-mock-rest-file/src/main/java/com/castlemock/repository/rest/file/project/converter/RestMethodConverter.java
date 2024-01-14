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

package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.repository.rest.file.project.model.RestMethodFile;

public final class RestMethodConverter {

    private RestMethodConverter() {

    }

    public static RestMethodFile toRestMethod(final RestMethod restMethod) {
        return RestMethodFile.builder()
                .id(restMethod.getId())
                .name(restMethod.getName())
                .httpMethod(restMethod.getHttpMethod())
                .status(restMethod.getStatus())
                .resourceId(restMethod.getResourceId())
                .currentResponseSequenceIndex(restMethod.getCurrentResponseSequenceIndex())
                .responseStrategy(restMethod.getResponseStrategy())
                .simulateNetworkDelay(restMethod.getSimulateNetworkDelay()
                        .orElse(null))
                .automaticForward(restMethod.getAutomaticForward()
                        .orElse(null))
                .forwardedEndpoint(restMethod.getForwardedEndpoint()
                        .orElse(null))
                .networkDelay(restMethod.getNetworkDelay()
                        .orElse(null))
                .defaultMockResponseId(restMethod.getDefaultMockResponseId()
                        .orElse(null))
                .defaultBody(restMethod.getDefaultBody()
                        .orElse(null))
                .build();
    }

}
