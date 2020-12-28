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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

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

    public static <T extends ExportContainer> String serialize(T exportContainer){
        try {
            final JAXBContext context = JAXBContext.newInstance(exportContainer.getClass());
            final Marshaller marshaller = context.createMarshaller();
            final StringWriter writer = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(exportContainer, writer);
            return writer.toString();
        } catch (JAXBException e) {
            LOGGER.error("Unable to serialize", e);
            throw new IllegalArgumentException("Unable to serialize");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends ExportContainer> T deserialize(final String raw,
                                                            final Class<? extends ExportContainer> clazz){
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (raw.getBytes());
            final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(byteArrayInputStream);
        } catch (JAXBException e) {
            LOGGER.error("Unable to deserialize", e);
            throw new IllegalStateException("Unable to deserialize");
        }
    }

}
