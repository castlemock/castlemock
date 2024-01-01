package com.castlemock.deploy.jetty.jar;

import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.FileRepositorySupport;
import com.castlemock.repository.soap.file.project.SoapMockResponseFileRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileRepositorySupportTest {

    @Test
    public void testSave() throws IOException{
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
        soapMockResponseFile.setBody("""

                <?xml version="1.0"?>

                <soap:Envelope
                xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
                soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">

                <soap:Body>
                  <m:GetPrice xmlns:m="https://www.w3schools.com/prices">
                    <m:Item>Apples Maçãs</m:Item>
                  </m:GetPrice>
                </soap:Body>

                </soap:Envelope>\s""");
        soapMockResponseFile.setHttpHeaders(headers);
        soapMockResponseFile.setXpathExpressions(expressions);

        Path saveFilePath = Files.createTempFile(null, ".res");
        
        fileRepositorySupport.save(soapMockResponseFile, saveFilePath.toString());
        String savedContent = fileRepositorySupport.load(saveFilePath.getParent().toString(), saveFilePath.getFileName().toString());
        
        Assertions.assertTrue(savedContent.contains("Apples Maçãs"));
        
        Files.deleteIfExists(saveFilePath);
    }

}
