/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapXPathExpression;

import java.util.Arrays;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapMockResponseGenerator {

    public static SoapMockResponse generateSoapMockResponse(){
        final SoapXPathExpression soapXPathExpression1 = new SoapXPathExpression();
        final SoapXPathExpression soapXPathExpression2 = new SoapXPathExpression();
        soapXPathExpression1.setExpression("//Request/Name[text()='Input1']");
        soapXPathExpression2.setExpression("//Request/Name[text()='Input2']");

        final SoapMockResponse soapMockResponse = new SoapMockResponse();
        soapMockResponse.setName("Soap mock response name");
        soapMockResponse.setBody("Soap mock response body");
        soapMockResponse.setId("SOAP MOCK RESPONSE");
        soapMockResponse.setXpathExpression("//Request/Name[text()='Input1']");
        soapMockResponse.setXpathExpressions(Arrays.asList(soapXPathExpression1, soapXPathExpression2));
        soapMockResponse.setStatus(SoapMockResponseStatus.ENABLED);
        return soapMockResponse;
    }
}
