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

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class MtomUtilityTest {

    @Test
    public void extractMtomBodyTest() throws IOException {
        String body = "------=_Part_64_1526053806.1517665317492\n" +
                "Content-Type: text/xml; charset=UTF-8\n" +
                "Content-Transfer-Encoding: 8bit\n" +
                "Content-ID: <test@castlemock.org>\n" +
                "\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cas=\"http://castlemock.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <cas:TestService>\n" +
                "         <Variable1>?</Variable1>\n" +
                "         <Variable2>\n" +
                "            <Variable1>?</Variable1>\n" +
                "            <Variable2>?</Variable2>\n" +
                "            <files/>\n" +
                "         </Variable2>\n" +
                "      </cas:TestService>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n" +
                "------=_Part_64_1526053806.1517665317492\n" +
                "Content-Type: text/plain; charset=us-ascii; name=\"example\"\n" +
                "Content-ID: <example>\n" +
                "Content-Disposition: attachment; name=\"example.txt\"; filename=\"example.txt\"\n" +
                "\n" +
                "This is an example\n" +
                "------=_Part_24_1742827313.1517654770545--";
        String contextType = "multipart/related; type=\"text/xml\"; start=\"<test@castlemock.org>\"; boundary=\"----=_Part_64_1526053806.1517665317492\"";

        String mainBody = MtomUtility.extractMtomBody(body, contextType);

        Assert.assertNotNull(mainBody);
        Assert.assertEquals("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cas=\"http://castlemock.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <cas:TestService>\n" +
                "         <Variable1>?</Variable1>\n" +
                "         <Variable2>\n" +
                "            <Variable1>?</Variable1>\n" +
                "            <Variable2>?</Variable2>\n" +
                "            <files/>\n" +
                "         </Variable2>\n" +
                "      </cas:TestService>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n", mainBody);
    }



}
