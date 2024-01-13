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

package com.castlemock.model.core.utility.serializer;

import com.castlemock.model.core.ExportContainer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ExportContainerSerializer} is a utility class that provides functionality to
 * serialize and eeserialize {@link ExportContainer}.
 * @author Karl Dahlgren
 * @since 1.20
 * @see ExportContainer
 */
public final class ExportContainerSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportContainerSerializer.class);

    private ExportContainerSerializer(){

    }

    public static <T extends ExportContainer> String serialize(final T exportContainer){
        try {
            final XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new Jdk8Module());
            xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            return xmlMapper.writeValueAsString(exportContainer);
        } catch (Throwable e) {
            LOGGER.error("Unable to serialize", e);
            throw new IllegalArgumentException("Unable to serialize", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends ExportContainer> T deserialize(final String raw,
                                                            final Class<? extends ExportContainer> clazz){
        try {
            final XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new Jdk8Module());
            return (T) xmlMapper.readValue(raw, clazz);
        } catch (Throwable e ) {
            LOGGER.error("Unable to deserialize", e);
            throw new IllegalStateException("Unable to deserialize", e);
        }
    }

}
