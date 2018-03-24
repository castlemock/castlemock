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

package com.castlemock.web.mock.soap.web.soap.controller;

import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.model.SoapErrorMessage;
import com.castlemock.web.mock.soap.model.SoapException;
import com.castlemock.web.mock.soap.web.AbstractControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class SoapControllerAdviceTest extends AbstractControllerTest {

    @InjectMocks
    private SoapControllerAdvice soapControllerAdvice;

    @Test
    public void testDefaultErrorWebServiceHandler(){
        final SoapException soapException = new SoapException("Error message");
        final SoapErrorMessage soapErrorMessage =
                soapControllerAdvice.defaultErrorWebServiceHandler(soapException);

        Assert.assertEquals("Error message", soapErrorMessage.getMessage());


    }

    @Override
    protected AbstractController getController() {
        return soapControllerAdvice;
    }
}
