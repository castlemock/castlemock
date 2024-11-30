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

package com.castlemock.model.mock.soap.domain;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlType
@XmlEnum
public enum SoapVersion {

    SOAP11("SOAP 1.1", "text/xml"), SOAP12("SOAP 1.2", "application/soap+xml");

    private final String name;
    private final String contextType;

    SoapVersion(final String name, final String contextType){
        this.name = Objects.requireNonNull(name, "name");
        this.contextType = Objects.requireNonNull(contextType, "contextType");
    }

    public String getName() {
        return name;
    }

    public String getContextType() {
        return contextType;
    }

    public static SoapVersion convert(final String contextType) {
        if(contextType.contains(SOAP12.getContextType())){
            return SOAP12;
        }

        // Return SOAP 1.1 as default
        return SOAP11;
    }
}
