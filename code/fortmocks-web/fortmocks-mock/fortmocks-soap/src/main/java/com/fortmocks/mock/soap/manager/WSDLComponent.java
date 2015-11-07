/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.mock.soap.manager;

import com.fortmocks.mock.soap.model.project.*;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;

import com.fortmocks.web.core.manager.FileManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.wsdl.*;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap12.SOAP12Address;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * WSDLComponent provides functionality to parse a WSDL file
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class WSDLComponent {

    @Autowired
    private FileManager fileManager;
    private static final String WSDL_SUFFIX = ".wsdl";
    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;
    private static final Logger LOGGER = Logger.getLogger(WSDLComponent.class);

    /**
     * The method provides the functionality to download a WSDL file from a specific URL, download it and generate
     * new SOAP ports.
     * @param wsdlURL The URL from where the WSDL file can be downloaded
     * @param generateResponse Boolean value for if a default response should be generated for each new operation. The
     *                         value true will cause the method to generate a default response for each new service. No
     *                         default responses will be created if the variable is set to false.
     * @return  The extracted ports from the downloaded WSDL file.
     */
    public List<SoapPortDto> createSoapPorts(final String wsdlURL, final boolean generateResponse) {
        try {
            LOGGER.debug("Starting to parse WSDL");
            final List<Definition> definitions = parseWSDL(wsdlURL);
            LOGGER.debug("Parsing done");
            return createPorts(definitions, generateResponse);
        } catch (WSDLException exception) {
            LOGGER.error("Unable to parse WSDL", exception);
            throw new IllegalStateException("Unable to parse the WSDL file");
        }
    }

    /**
     * Parse an WSDL file and creates new ports based on the WSDL file
     * @param files The list of WSDL files
     * @param generateResponse Boolean value for if a default response should be generated for each new operation. The
     *                         value true will cause the method to generate a default response for each new service. No
     *                         default responses will be created if the variable is set to false.
     * @return The extracted ports from the WSDL file.
     */
    public List<SoapPortDto> createSoapPorts(final List<MultipartFile> files, final boolean generateResponse){
        List<File> uploadedFiles = new ArrayList<File>();
        try {
            LOGGER.debug("Starting to upload file");
            uploadedFiles = fileManager.uploadFiles(files);
            LOGGER.debug("Uploading file complete");
            LOGGER.debug("Starting to parse WSDL");
            final List<Definition> definitions = parseWSDL(uploadedFiles);
            LOGGER.debug("Parsing done");
            return createPorts(definitions, generateResponse);
        } catch (Exception exception) {
            LOGGER.error("Unable to parse WSDL", exception);
            throw new IllegalStateException("Unable to parse WSDL", exception);
        } finally {
            fileManager.deleteUploadedFiles(uploadedFiles);
        }
    }

    /**
     * The method creates ports based on provided definitions.
     * @param definitions Definitions used to create new ports.
     * @param generateResponse Boolean value for if a default response should be generated for each new operation
     * @return List of ports generated from the provided definitions.
     * @throws WSDLException Throws an WSDLException if WSDL parsing failed.
     */
    private List<SoapPortDto> createPorts(final List<Definition> definitions, final boolean generateResponse) throws WSDLException {
        final List<SoapPortDto> soapPorts = new ArrayList<SoapPortDto>();

        for(Definition definition : definitions){
            final String targetNamespace = definition.getTargetNamespace();
            for(Object serviceObject: definition.getServices().values()){
                Service service = (Service) serviceObject;
                for(Object portObject : service.getPorts().values()){
                    final Port port = (Port) portObject;

                    final SoapOperationType type = getSoapType(port);

                    if(type == null){
                        continue;
                    }

                    final String originalEndpoint = getLocationUri(port);
                    final String uri = service.getQName().getLocalPart();

                    final SoapPortDto soapPort = new SoapPortDto();
                    soapPort.setName(port.getName());

                    final List<SoapOperationDto> services = new ArrayList<SoapOperationDto>();
                    final PortType portType = port.getBinding().getPortType();
                    for(Object operationObject : portType.getOperations()){
                        final Operation operation = (Operation) operationObject;
                        final String defaultBody = generateDefaultBody(operation, targetNamespace);
                        final SoapOperationDto soapService = new SoapOperationDto();
                        soapService.setName(operation.getName());
                        soapService.setSoapOperationStatus(SoapOperationStatus.MOCKED);
                        soapService.setSoapOperationMethod(SoapOperationMethod.POST);
                        soapService.setSoapResponseStrategy(SoapResponseStrategy.RANDOM);
                        soapService.setUri(uri);
                        soapService.setSoapOperationType(type);
                        soapService.setDefaultBody(defaultBody);
                        soapService.setCurrentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX);
                        soapService.setForwardedEndpoint(originalEndpoint);
                        soapService.setOriginalEndpoint(originalEndpoint);

                        if(generateResponse){
                            final SoapMockResponseDto mockResponse = new SoapMockResponseDto();
                            mockResponse.setBody(defaultBody);
                            mockResponse.setSoapMockResponseStatus(SoapMockResponseStatus.ENABLED);
                            mockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
                            soapService.getSoapMockResponses().add(mockResponse);
                        }


                        services.add(soapService);
                    }
                    soapPort.setSoapOperations(services);
                    soapPorts.add(soapPort);
                }
            }
        }
        return soapPorts;
    }

    /**
     * Get the specific SOAP type for a port
     * @param port The provided port contain information about the SOAP type
     * @return ServiceType with the value of the SOAP type
     */
    private SoapOperationType getSoapType(final Port port){
        for(Object extensibilityElement : port.getExtensibilityElements()){
            if(extensibilityElement instanceof SOAPAddress){
                return SoapOperationType.SOAP11;
            } else if(extensibilityElement instanceof SOAP12Address){
                return SoapOperationType.SOAP12;
            }
        }
        return null;
    }

    /**
     * Retrieves the location uri from a port
     * @param port The port contains the location uri
     * @return The location uri in String format
     */
    private String getLocationUri(final Port port){
        for(Object extensibilityElement : port.getExtensibilityElements()){
            if(extensibilityElement instanceof SOAPAddress){
                SOAPAddress soapAddress = (SOAPAddress) extensibilityElement;
                String locationUri = soapAddress.getLocationURI();

                if(locationUri != null){
                    return locationUri;
                }
            } else if(extensibilityElement instanceof SOAP12Address){
                SOAP12Address soapAddress12 = (SOAP12Address) extensibilityElement;
                String locationUri = soapAddress12.getLocationURI();

                if(locationUri != null){
                    return locationUri;
                }
            }
        }
        return null;
    }


    /**
     * Parse incoming WSDL files and extract the WSDL definitions
     * @param files The incoming WSDL files
     * @return A list of WSDL definitions
     * @throws WSDLException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private List<Definition> parseWSDL(final List<File> files) throws WSDLException, ParserConfigurationException, IOException, SAXException {
        final List<Definition> definitions = new ArrayList<Definition>();
        for(File file : files){
            if(file.getName().toLowerCase().endsWith(WSDL_SUFFIX)){
                LOGGER.debug("File: " + file.getName());
                final Definition definition = loadWSDL(file);
                LOGGER.debug("Definition: " + definition.getQName());
                definitions.add(definition);
            }
        }

        return definitions;
    }

    /**
     * Retrieve and parse a WSDL file located on the provided URL
     * @param url The location of the WSDL file
     * @return A list of definitions
     * @throws WSDLException
     */
    public List<Definition> parseWSDL(final String url) throws WSDLException {
        final List<Definition> definitions = new ArrayList<Definition>();
        final Definition definition = loadWSDL(url);
        definitions.add(definition);
        return definitions;
    }

    /**
     * Loads and parse a WSDL file
     * @param file The WSDL file that will be parsed and loaded and returned as a definition
     * @return The extracted definition from the provided file
     * @throws WSDLException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Definition loadWSDL(final File file) throws WSDLException, ParserConfigurationException, IOException, SAXException {
        final WSDLReader wsdlReader = WSDLFactory.newInstance().newWSDLReader();
        return wsdlReader.readWSDL(file.getAbsolutePath());
    }


    /**
     * Loads and parse a WSDL file
     * @param url The location of the WSDL file that will be parsed
     * @return The extracted definition from the provided file
     * @throws WSDLException
     */
    private Definition loadWSDL(final String url) throws WSDLException {
        final WSDLReader wsdlReader = WSDLFactory.newInstance().newWSDLReader();
        return wsdlReader.readWSDL(url);
    }


    /**
     * The method provides the functionality to generate a new mocked response
     * @param operation The operation contains all the information that is needed to compose a operation default body
     * @param targetNamespace The target namespace that the response will use
     * @return A string value of the response
     */
    public static String generateDefaultBody(final Operation operation, final String targetNamespace){
        final String prefix = "web";
        final String resultElement = operation.getName() + "Result";
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" + prefix + "=\"" + targetNamespace + "\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <" + prefix + ":" + resultElement + ">?</" + prefix + ":" + resultElement + ">\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}

