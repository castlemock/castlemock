package com.castlemock.deploy.jetty.jar;

import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.FileRepositorySupport;
import com.castlemock.repository.soap.file.project.SoapMockResponseFileRepository;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

public class FileRepositorySupportTest {

    @Test
    public void testSave(){
        final FileRepositorySupport fileRepositorySupport = new FileRepositorySupport();
        final FileRepository.HttpHeaderFile httpHeaderFile = new FileRepository.HttpHeaderFile();
        final SoapMockResponseFileRepository.SoapMockResponseFile soapMockResponseFile = new SoapMockResponseFileRepository.SoapMockResponseFile();
        final SoapMockResponseFileRepository.SoapXPathExpressionFile soapXPathExpressionFile = new SoapMockResponseFileRepository.SoapXPathExpressionFile();
        final CopyOnWriteArrayList<FileRepository.HttpHeaderFile> headers = new CopyOnWriteArrayList<>();
        final CopyOnWriteArrayList<SoapMockResponseFileRepository.SoapXPathExpressionFile> expressions = new CopyOnWriteArrayList<>();

        soapXPathExpressionFile.setExpression("Expression");
        expressions.add(soapXPathExpressionFile);

        httpHeaderFile.setName("Name");
        httpHeaderFile.setValue("Value");
        headers.add(httpHeaderFile);
        soapMockResponseFile.setId("Id");
        soapMockResponseFile.setName("Name");
        soapMockResponseFile.setBody("\n" +
                "<?xml version=\"1.0\"?>\n" +
                "\n" +
                "<soap:Envelope\n" +
                "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n" +
                "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
                "\n" +
                "<soap:Body>\n" +
                "  <m:GetPrice xmlns:m=\"https://www.w3schools.com/prices\">\n" +
                "    <m:Item>Apples</m:Item>\n" +
                "  </m:GetPrice>\n" +
                "</soap:Body>\n" +
                "\n" +
                "</soap:Envelope> ");
        soapMockResponseFile.setHttpHeaders(headers);
        soapMockResponseFile.setXpathExpressions(expressions);

        // TODO Resolve test
        //fileRepositorySupport.save(soapMockResponseFile, "/home/karl/.castlemock/soap/response/v2/0qirU2.res");
    }

}
