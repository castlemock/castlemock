package com.fortmocks.web.mock.soap.web.mvc.controller;

import com.fortmocks.web.mock.soap.model.SoapErrorMessage;
import com.fortmocks.web.mock.soap.model.SoapException;
import com.fortmocks.web.mock.soap.web.soap.controller.SoapControllerAdvice;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapControllerAdviceTest {

    @Test
    public void testDefaultErrorWebServiceHandler(){
        final SoapException soapException = new SoapException("SOAP exception");
        final SoapControllerAdvice soapControllerAdvice = new SoapControllerAdvice();
        final SoapErrorMessage soapErrorMessage = soapControllerAdvice.defaultErrorWebServiceHandler(soapException);
        Assert.assertEquals(soapException.getMessage(), soapErrorMessage.getMessage());
    }

}
