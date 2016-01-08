package com.fortmocks.web.mock.soap.support;

import com.fortmocks.web.mock.soap.model.SoapException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Karl Dahlgren
 * @since 1.1
 * @see SoapMessageSupport
 */
public class SoapMessageSupportTest {

    @Test
    public void testGetBody(){
        final String readerOutput = "This is the output from the reader";
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final BufferedReader reader = Mockito.mock(BufferedReader.class);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(reader);
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }

        try {
            Mockito.when(reader.readLine()).thenReturn(readerOutput, null);
        } catch (IOException e) {
            Assert.fail("Unable to mock readLine method for BufferedReader");
        }

        final String output = SoapMessageSupport.getBody(httpServletRequest);
        Assert.assertEquals(readerOutput, output);
    }

    @Test(expected = SoapException.class)
    public void testGetBodyRequestIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        try {
            Mockito.when(httpServletRequest.getReader()).thenThrow(new IOException());
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }
        SoapMessageSupport.getBody(httpServletRequest);
    }

    @Test(expected = SoapException.class)
    public void testGetBodyReaderIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final BufferedReader reader = Mockito.mock(BufferedReader.class);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(reader);
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }

        try {
            Mockito.when(reader.readLine()).thenThrow(new IOException());
        } catch (IOException e) {
            Assert.fail("Unable to mock readLine method for BufferedReader");
        }
        SoapMessageSupport.getBody(httpServletRequest);
    }

    @Test
    public void testGetElement(){
        final String oneInput = "First";
        final String twoInput = "First:Second";

        Assert.assertEquals("First", SoapMessageSupport.getElement(oneInput, 0));
        Assert.assertEquals("First", SoapMessageSupport.getElement(oneInput, 1));
        Assert.assertEquals("First", SoapMessageSupport.getElement(twoInput, 0));
        Assert.assertEquals("Second", SoapMessageSupport.getElement(twoInput, 1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidIndexLessThanZero(){
        Assert.assertEquals("First", SoapMessageSupport.getElement("First", -1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidIndexMoreThanOne(){
        Assert.assertEquals("First", SoapMessageSupport.getElement("First", 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidElement(){
        Assert.assertEquals("First", SoapMessageSupport.getElement("First:Second:Third", 1));
    }

    @Test
    public void testExtractSoapRequestName(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                "<soap:Envelope\n" +
                "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n" +
                "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
                "<soap:Body>\n" +
                "  <m:GetPrice xmlns:m=\"http://www.w3schools.com/prices\">\n" +
                "    <m:Item>Apples</m:Item>\n" +
                "  </m:GetPrice>\n" +
                "</soap:Body>\n" +
                "</soap:Envelope> ";

        final String requestName = SoapMessageSupport.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", requestName);
    }

    @Test(expected = SoapException.class)
    public void testExtractSoapRequestNameInvalidRequestBody(){
        SoapMessageSupport.extractSoapRequestName(new String());
    }
}
