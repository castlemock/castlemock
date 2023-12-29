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

package com.castlemock.service.mock.soap.project.converter.types;

import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.service.core.utility.DocumentUtility;
import com.castlemock.service.mock.soap.utility.SoapUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class WsdlServiceParser extends WsdlParser {

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String SOAP_11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP_12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";
    private static final String PORT_NAMESPACE = "port";
    private static final String NAME_NAMESPACE = "name";
    private static final String BINDING_NAMESPACE = "binding";
    private static final String SERVICE_NAMESPACE = "service";

    public Set<Service> parseServices(final Document document){
        final List<Element> serviceElements = DocumentUtility.getElements(document, WSDL_NAMESPACE, SERVICE_NAMESPACE);
        return serviceElements.stream()
                .map(this::parseService)
                .collect(Collectors.toSet());
    }


    private Service parseService(final Element serviceElement){
        final List<Element> portElements = DocumentUtility.getElements(serviceElement, WSDL_NAMESPACE, PORT_NAMESPACE);
        final String name = DocumentUtility.getAttribute(serviceElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find service name"));
        final Set<ServicePort> ports = portElements.stream()
                .map(this::parseServicePort)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
        return Service.builder()
                .name(name)
                .ports(ports)
                .build();
    }

    private Optional<ServicePort> parseServicePort(final Element servicePortElement){
        final Optional<ServicePortAddress> address = parseServicePortAddress(servicePortElement);

        if(address.isEmpty()){
            return Optional.empty();
        }

        final String name = DocumentUtility.getAttribute(servicePortElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find service port name"));
        final Attribute binding = this.getAttribute(servicePortElement, BINDING_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find binding attribute"));


        return Optional.ofNullable(ServicePort.builder()
                .address(address.get())
                .binding(binding)
                .name(name)
                .build());
    }

    private Optional<ServicePortAddress> parseServicePortAddress(final Element servicePortElement){
        Optional<String> location = SoapUtility.extractSoapAddress(servicePortElement, SOAP_11_NAMESPACE);
        SoapVersion version = SoapVersion.SOAP11;

        if(location.isEmpty()){
            location = SoapUtility.extractSoapAddress(servicePortElement, SOAP_12_NAMESPACE);
            version = SoapVersion.SOAP12;
        }
        if(location.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(ServicePortAddress.builder()
                .location(location.get())
                .version(version)
                .build());
    }

}
