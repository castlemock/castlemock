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

package com.castlemock.service.mock.soap.project.converter;

import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapResourceType;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.core.utility.DocumentUtility;
import com.castlemock.service.core.utility.UrlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SoapPortConverter {

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String IMPORT_NAMESPACE = "import";
    private static final String LOCATION_NAMESPACE = "location";
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapPortConverter.class);

    @Autowired
    private FileManager fileManager;

    public Set<SoapPortConverterResult> getSoapPorts(final List<File> files,
                                       final boolean generateResponse){
        final Map<String, WSDLDocument> documents = this.getDocuments(files, SoapResourceType.WSDL);
        return getResults(documents, generateResponse);
    }

    public Set<SoapPortConverterResult> getSoapPorts(final String location,
                                                     final boolean generateResponse,
                                                     final boolean loadExternal){
        try {
            final List<File> files = this.fileManager.uploadFiles(location);
            final Map<String, WSDLDocument> documents = this.getDocuments(files, SoapResourceType.WSDL);
            final Map<String, WSDLDocument> allDocuments = new HashMap<>(documents);

            if(loadExternal){
                documents.values()
                        .forEach(document -> loadExternal(location, document, allDocuments));
            }

            return getResults(allDocuments, generateResponse);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Set<SoapPortConverterResult> getResults(final Map<String, WSDLDocument> documents,
                                                    final boolean generateResponse){
        try {
            return documents.entrySet().stream()
                    .map(documentEntry -> {
                        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                        String name = documentEntry.getKey();
                        final WSDLDocument wsdlDocument = documentEntry.getValue();
                        final Document document = wsdlDocument.getDocument();
                        final String content = DocumentUtility.toString(document);
                        try {
                            DocumentBuilder builder = builderFactory.newDocumentBuilder();
                            Document xmlDocument = builder.parse(new InputSource(new StringReader(content)));
                            try {
                                XPath xPath = XPathFactory.newInstance().newXPath();
                                String path = "/definitions/portType/@name";
                                Node node = (Node) xPath.compile(path).evaluate(xmlDocument, XPathConstants.NODE);
                                name = node.getNodeValue();
                            } catch (XPathExpressionException e) {
                                e.printStackTrace();
                            }
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final Set<SoapPort> ports = DocumentConverter.toSoapParts(document, generateResponse);

                        return SoapPortConverterResult.builder()
                                .name(name)
                                .ports(ports)
                                .definition(content)
                                .resourceType(wsdlDocument.getResourceType())
                                .build();
                    })
                    .collect(Collectors.toSet());
        }catch (Exception exception) {
            LOGGER.error("Unable to parse WSDL: " + exception.getMessage(), exception);
            throw exception;
        }
    }

    private Map<String, WSDLDocument> getDocuments(final List<File> files,
                                                   final SoapResourceType resourceType){
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);

            final Map<String, WSDLDocument> documents = new HashMap<>();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            for(File file : files){
                final Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();
                documents.put(file.getName(), WSDLDocument.builder()
                        .document(document)
                        .definition(resourceType)
                        .build());
            }

            return documents;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void loadExternal(final String url,
                              final WSDLDocument wsdlDocument,
                              final Map<String, WSDLDocument> documents){
        try {
            final Set<String> externalPaths = this.getImports(url, wsdlDocument);
            for(String externalPath : externalPaths){
                final List<File> files = fileManager.uploadFiles(externalPath);
                final Map<String, WSDLDocument> externalDocuments =
                        this.getDocuments(files, SoapResourceType.WSDL_IMPORT);
                documents.putAll(externalDocuments);

                for(WSDLDocument externalWsdlDocument : externalDocuments.values()){
                    loadExternal(externalPath, externalWsdlDocument, documents);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Set<String> getImports(final String url,
                                     final WSDLDocument wsdlDocument){
        final Document document = wsdlDocument.getDocument();
        final List<Element> importElements = DocumentUtility.getElements(document, WSDL_NAMESPACE, IMPORT_NAMESPACE);

        return importElements.stream()
                .map(importElement -> DocumentUtility.getAttribute(importElement, LOCATION_NAMESPACE))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(location -> UrlUtility.getPath(url, location))
                .collect(Collectors.toSet());
    }

}
