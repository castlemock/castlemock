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

package com.castlemock.model.mock.soap;

import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;

import java.util.List;

public final class SoapExportContainerTestBuilder {

    private SoapExportContainerTestBuilder() {

    }

    public static SoapExportContainer.Builder builder() {
        return SoapExportContainer.builder()
                .project(SoapProjectTestBuilder.builder().build())
                .ports(List.of(SoapPortTestBuilder.builder().build()))
                .operations(List.of(SoapOperationTestBuilder.builder().build()))
                .mockResponses(List.of(SoapMockResponseTestBuilder.builder().build()))
                .resources(List.of(SoapResourceTestBuilder.builder().build()));
    }

    public static SoapExportContainer build() {
        return builder().build();
    }

}
