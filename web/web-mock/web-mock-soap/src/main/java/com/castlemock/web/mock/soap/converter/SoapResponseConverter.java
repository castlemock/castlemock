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

package com.castlemock.web.mock.soap.converter;

import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.service.mock.soap.project.input.CreateSoapMockResponseInput;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SoapResponseConverter {

    private static final String RECORDED_RESPONSE_NAME = "Recorded response";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String SPACE = " ";

    private SoapResponseConverter() {

    }

    public static CreateSoapMockResponseInput toCreateSoapMockResponseInput(final SoapResponse response,
                                                                            final String projectId,
                                                                            final String portId,
                                                                            final SoapOperation operation) {
        return CreateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .body(response.getBody())
                .status(SoapMockResponseStatus.ENABLED)
                .name(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(new Date()))
                .httpHeaders(response.getHttpHeaders())
                .httpStatusCode(response.getHttpStatusCode())
                .usingExpressions(Boolean.FALSE)
                .build();
    }

}
