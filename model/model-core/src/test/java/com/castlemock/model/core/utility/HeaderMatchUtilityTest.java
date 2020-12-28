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

package com.castlemock.model.core.utility;

import com.castlemock.model.core.model.http.domain.HttpHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeaderMatchUtilityTest {

    @Test
    public void testExpression1() {
        final List<HttpHeader> headers = getHeaders();
        final String expression = "OperationType -> Test";
        final boolean result = HeaderMatchUtility.isValidHeaderParameterExpr(headers, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression2() {
        final List<HttpHeader> headers = getHeaders();
        final String expression = "OperationType = Test";
        final boolean result = HeaderMatchUtility.isValidHeaderParameterExpr(headers, expression);
        assertFalse(result);
    }

    @Test
    public void testExpression3() {
        final List<HttpHeader> headers = getHeaders();
        final String expression = "OperationType -> keep-alive";
        final boolean result = HeaderMatchUtility.isValidHeaderParameterExpr(headers, expression);
        assertFalse(result);
    }

    @Test
    public void testExpression4() {
        final List<HttpHeader> headers = getHeaders();
        final String expression = "connection -> keep-alive";
        final boolean result = HeaderMatchUtility.isValidHeaderParameterExpr(headers, expression);
        assertTrue(result);
    }

    @Test
    public void testExpression5() {
        final List<HttpHeader> headers = getHeaders();
        final String expression = "Content-Type -> text/plain";
        final boolean result = HeaderMatchUtility.isValidHeaderParameterExpr(headers, expression);
        assertTrue(result);
    }

    private List<HttpHeader> getHeaders() {
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.setName("Content-Type");
        httpHeader.setValue("text/plain");

        HttpHeader httpHeader2 = new HttpHeader();
        httpHeader2.setName("connection");
        httpHeader2.setValue("keep-alive");

        HttpHeader httpHeader3 = new HttpHeader();
        httpHeader3.setName("OperationType");
        httpHeader3.setValue("Test");

        List<HttpHeader> headers = new ArrayList<>();
        headers.add(httpHeader);
        headers.add(httpHeader2);
        headers.add(httpHeader3);

        return headers;
    }

}
