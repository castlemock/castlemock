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

package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.repository.soap.file.project.model.SoapResourceFile;

public final class SoapResourceConverter {

    private SoapResourceConverter() {

    }

    public static SoapResourceFile toSoapResourceFile(final SoapResource soapResource) {
        return SoapResourceFile.builder()
                .id(soapResource.getId())
                .name(soapResource.getName())
                .projectId(soapResource.getProjectId())
                .type(soapResource.getType())
                .build();
    }

}
