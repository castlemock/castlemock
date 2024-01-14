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

package com.castlemock.model.mock.soap.domain;

import java.util.Date;

public final class SoapEventTestBuilder {

    private SoapEventTestBuilder() {

    }

    public static SoapEvent.Builder builder() {
        return SoapEvent.builder()
                .endDate(new Date())
                .id("SOAP EVENT")
                .operationId("OperationId")
                .portId("PortId")
                .projectId("Project id")
                .request(SoapRequestTestBuilder.builder().build())
                .response(SoapResponseTestBuilder.builder().build())
                .resourceName("resource name")
                .startDate(new Date());
    }

    public static SoapEvent build() {
        return builder().build();
    }

}
