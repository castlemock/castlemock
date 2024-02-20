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

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.service.mock.soap.utility.SoapUtility;
import com.castlemock.web.core.utility.HttpMessageSupport;
import com.castlemock.web.mock.soap.utility.MtomUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Set;

public final class HttpServletRequestConverter {

    private static final String EMPTY = "";
    private static final String MOCK = "mock";
    private static final String PROJECT = "project";
    private static final String SLASH = "/";
    private static final String SOAP = "soap";


    private HttpServletRequestConverter() {

    }

    /**
     * The method prepares an request
     *
     * @param projectId          The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    public static SoapRequest toSoapRequest(final HttpServletRequest httpServletRequest,
                                            final String projectId, final String contextPath) {
        final String body = HttpMessageSupport.getBody(httpServletRequest);

        final SoapOperationIdentifier identifier;
        final String envelope;
        if (httpServletRequest instanceof MultipartHttpServletRequest) {
            // Check if the request is a Multipart request. If so, interpret  the incoming request
            // as a MTOM request and extract the main body (Exclude the attachment).
            // MTOM request mixes both the attachments and the body in the HTTP request body.
            envelope = MtomUtility.extractMtomBody(body, httpServletRequest.getContentType());

            // Use the main body to identify
            identifier = SoapUtility.extractSoapRequestName(envelope);
        } else {
            // The incoming request is a regular SOAP request. Parse the body as it is.
            envelope = body;
            identifier = SoapUtility.extractSoapRequestName(body);
        }

        final String serviceUri = httpServletRequest.getRequestURI().replace(contextPath +
                SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + projectId + SLASH, EMPTY);
        final Set<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        final SoapVersion type = SoapVersion.convert(httpServletRequest.getContentType());
        return SoapRequest.builder()
                .soapVersion(type)
                .httpHeaders(httpHeaders)
                .uri(serviceUri)
                .httpMethod(HttpMethod.valueOf(httpServletRequest.getMethod()))
                .body(body)
                .envelope(envelope)
                .operationIdentifier(identifier)
                .contentType(httpServletRequest.getContentType())
                .build();
    }

}
