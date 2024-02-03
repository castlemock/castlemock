/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.support;

import com.castlemock.web.mock.soap.utility.MtomUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class MtomUtilityTest {

    @Test
    public void extractMtomBodyTest() {
        String body = """
                ------=_Part_64_1526053806.1517665317492
                Content-Type: text/xml; charset=UTF-8
                Content-Transfer-Encoding: 8bit
                Content-ID: <test@castlemock.org>

                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cas="http://castlemock.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <cas:TestService>
                         <Variable1>?</Variable1>
                         <Variable2>
                            <Variable1>?</Variable1>
                            <Variable2>?</Variable2>
                            <files/>
                         </Variable2>
                      </cas:TestService>
                   </soapenv:Body>
                </soapenv:Envelope>
                ------=_Part_64_1526053806.1517665317492
                Content-Type: text/plain; charset=us-ascii; name="example"
                Content-ID: <example>
                Content-Disposition: attachment; name="example.txt"; filename="example.txt"

                This is an example
                ------=_Part_24_1742827313.1517654770545--""";
        String contextType = "multipart/related; type=\"text/xml\"; start=\"<test@castlemock.org>\"; boundary=\"----=_Part_64_1526053806.1517665317492\"";

        String mainBody = MtomUtility.extractMtomBody(body, contextType);

        Assertions.assertNotNull(mainBody);
        Assertions.assertEquals("""
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cas="http://castlemock.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <cas:TestService>
                         <Variable1>?</Variable1>
                         <Variable2>
                            <Variable1>?</Variable1>
                            <Variable2>?</Variable2>
                            <files/>
                         </Variable2>
                      </cas:TestService>
                   </soapenv:Body>
                </soapenv:Envelope>
                """, mainBody);
    }



}
