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

public final class RestMethodFileConverter {

    private RestMethodFileConverter() {

    }

    public static RestMethod toRestMethod(final RestMethodFile restMethodFile) {
        return RestMethod.builder()
                .id(restMethodFile.getId())
                .name(restMethodFile.getName())
                .httpMethod(restMethodFile.getHttpMethod())
                .status(restMethodFile.getStatus())
                .resourceId(restMethodFile.getResourceId())
                .forwardedEndpoint(restMethodFile.getForwardedEndpoint())
                .networkDelay(restMethodFile.getNetworkDelay())
                .defaultMockResponseId(restMethodFile.getDefaultMockResponseId())
                .currentResponseSequenceIndex(restMethodFile.getCurrentResponseSequenceIndex())
                .defaultBody(restMethodFile.getDefaultBody())
                .responseStrategy(restMethodFile.getResponseStrategy())
                .simulateNetworkDelay(restMethodFile.getSimulateNetworkDelay())
                .automaticForward(restMethodFile.getAutomaticForward())
                .uri(null)
                .build();
    }

}
