/*
 * Copyright 2023 Karl Dahlgren
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
package com.castlemock.web.mock.rest.utility;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestRequest;
import com.castlemock.model.mock.rest.domain.RestResponse;
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
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private static final String FORWARDED_RESPONSE_NAME = "Forwarded response";

    public Optional<RestResponse> getResponse(final RestRequest request, final RestMethod restMethod) {
        HttpURLConnection connection = null;
        try {

            String requestBody = null;

            if (HttpMethod.POST.equals(request.getHttpMethod()) ||
                HttpMethod.PUT.equals(request.getHttpMethod()) ||
                HttpMethod.DELETE.equals(request.getHttpMethod())) {
                requestBody = request.getBody().orElse(null);
            }

            final String parameterUri = HttpMessageSupport.buildParameterUri(request.getHttpParameters());
            final String endpoint = restMethod.getForwardedEndpoint() + request.getUri() + parameterUri;

            connection = HttpMessageSupport.establishConnection(
                endpoint,
                request.getHttpMethod(),
                requestBody,
                request.getHttpHeaders());

            final List<HttpContentEncoding> encodings = HttpMessageSupport.extractContentEncoding(connection);
            final List<HttpHeader> responseHttpHeaders = HttpMessageSupport.extractHttpHeaders(connection);
            final String characterEncoding = CharsetUtility.parseHttpHeaders(responseHttpHeaders);
            final String responseBody = HttpMessageSupport.extractHttpBody(connection, encodings, characterEncoding);
            return Optional.of(
                RestResponse.builder()
                .body(responseBody)
                .mockResponseName(FORWARDED_RESPONSE_NAME)
                .httpHeaders(responseHttpHeaders)
                .httpStatusCode(connection.getResponseCode())
                .contentEncodings(encodings)
                .build()
            );
        } catch (
            IOException exception) {
            LOGGER.error("Unable to forward request", exception);
            return Optional.empty();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
