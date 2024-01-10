/*
 * Copyright 2021 Karl Dahlgren
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
package com.castlemock.web.mock.soap.controller.mock;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.web.core.utility.CharsetUtility;
import com.castlemock.web.core.utility.HttpMessageSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;

@Component
public class SoapClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);
    private static final String FORWARDED_RESPONSE_NAME = "Forwarded response";

    public Optional<SoapResponse> getResponse(final SoapRequest request,
                                              final SoapOperation soapOperation) {
        HttpURLConnection connection = null;
        try {
            connection = HttpMessageSupport.establishConnection(
                soapOperation.getForwardedEndpoint()
                        .orElseThrow(() -> new IllegalStateException("Unable to extract forwarded endpoint")),
                request.getHttpMethod(),
                request.getBody(),
                request.getHttpHeaders());

            final Integer responseCode = connection.getResponseCode();
            final List<HttpContentEncoding> encodings = HttpMessageSupport.extractContentEncoding(connection);
            final List<HttpHeader> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            final String characterEncoding = CharsetUtility.parseHttpHeaders(responseHttpHeaders);
            final String responseBody = HttpMessageSupport.extractHttpBody(connection, encodings, characterEncoding);
            final SoapResponse response = SoapResponse.builder()
                .mockResponseName(FORWARDED_RESPONSE_NAME)
                .body(responseBody)
                .httpHeaders(responseHttpHeaders)
                .httpStatusCode(responseCode)
                .contentEncodings(encodings)
                .build();
            return Optional.of(response);
        } catch (IOException exception){
            LOGGER.error("Unable to forward request", exception);
            return Optional.empty();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }
}
