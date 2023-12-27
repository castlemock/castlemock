package com.castlemock.model.core.utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mustafa Kerim Yilmaz
 * @since 1.66
 */
public class XsltUtilityTest {

    @Test
    public void testGetXPathValue(){
        final String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.webservicex.net\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <web:GetWhoIS>\n" +
                "         <web:HostName>castlemock.com</web:HostName>\n" +
                "      </web:GetWhoIS>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
                "   <xsl:template match=\"/\">" +
                "      <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.castlemock.com/\">\n" +
                "         <soapenv:Header/>\n" +
                "         <soapenv:Body>\n" +
                "            <web:response>\n" +
                "               <web:value><xsl:value-of select=\"3+3\"/></web:value>\n" +
                "            </web:response>\n" +
                "         </soapenv:Body>\n" +
                "      </soapenv:Envelope>" +
                "   </xsl:template>" +
                "</xsl:stylesheet>";

        String result = XsltUtility.transform(body, response);
        assertTrue(result.contains("<web:value>6</web:value>"));
    }

}
