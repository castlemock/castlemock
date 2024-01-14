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

import java.util.List;
import java.util.Map;

public final class SoapPortTestBuilder {


    private SoapPortTestBuilder() {

    }

    public static SoapPort.Builder builder(){
        return SoapPort.builder()
                .id("SOAP PORT")
                .invokeAddress("soapproject")
                .name("Soap port name")
                .operations(List.of())
                .projectId("SOAP PROJECT")
                .statusCount(Map.of())
                .uri( "UrlPath");
    }

    public static SoapPort build() {
        return builder().build();
    }

}
