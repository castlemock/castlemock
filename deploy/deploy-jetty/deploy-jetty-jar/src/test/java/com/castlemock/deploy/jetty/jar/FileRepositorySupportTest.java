package com.castlemock.deploy.jetty.jar;

import org.junit.Test;

import java.io.IOException;

public class FileRepositorySupportTest {

    @Test
    public void testSave() throws IOException{
        /*
        final FileRepositorySupport fileRepositorySupport = new FileRepositorySupport();
        final HttpHeaderFile httpHeaderFile = HttpHeaderFile.builder()
                .name("Name")
                .value("Value")
                .build();
        final SoapMockResponseFile soapMockResponseFile = new  SoapMockResponseFile();
        final SoapXPathExpressionFile soapXPathExpressionFile = new  SoapXPathExpressionFile();
        final CopyOnWriteArrayList<HttpHeaderFile> headers = new CopyOnWriteArrayList<>();
        final CopyOnWriteArrayList< SoapXPathExpressionFile> expressions = new CopyOnWriteArrayList<>();

        soapXPathExpressionFile.setExpression("Expression");
        expressions.add(soapXPathExpressionFile);

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

         */
    }

}
