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

import org.w3c.dom.Element;

import java.util.Optional;

abstract class WsdlParser {

    /**
     * Extracts an attribute from an element. The method will also remove namespace prefix
     * if it is present.
     * @param element The element which the attribute will be extracted from
     * @param name The name of the attribute that will be extracted
     * @return The attribute value
     */
    protected Optional<Attribute> getAttribute(final Element element,
                                                      final String name){
        final String value = element.getAttribute(name);
        if(value.isEmpty()){
            return Optional.empty();
        }

        String[] splitValues = value.split(":");

        if(splitValues.length == 1) {
            return Optional.of(Attribute.builder()
                    .value(value)
                    .localName(splitValues[0])
                    .build());
        } else if(splitValues.length == 2) {
            return Optional.of(Attribute.builder()
                    .namespace(splitValues[0])
                    .localName(splitValues[1])
                    .value(value)
                    .build());
        }

        return Optional.empty();
    }
}
