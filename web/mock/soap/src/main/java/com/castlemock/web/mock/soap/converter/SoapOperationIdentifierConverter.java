package com.castlemock.web.mock.soap.converter;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;

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
                prefix + "=\"" + operationResponseIdentifier.getNamespace() + "\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <" + prefix + ":" + operationResponseIdentifier.getName() + ">?</" + prefix + ":" +
                operationResponseIdentifier.getName() + ">\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

}
