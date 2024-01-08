/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.service.mock.soap.project.converter;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;

public final class SoapOperationIdentifierConverter {

    private SoapOperationIdentifierConverter(){

    }

    /**
     * The method provides the functionality to generate a new mocked response
     * @return A string value of the response
     */
    public static String toDefaultBody(final SoapOperationIdentifier operationResponseIdentifier){
        final String prefix = "web";
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" +
                prefix + "=\"" + operationResponseIdentifier.getNamespace().orElse("") + "\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <" + prefix + ":" + operationResponseIdentifier.getName() + ">?</" + prefix + ":" +
                operationResponseIdentifier.getName() + ">\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

}
