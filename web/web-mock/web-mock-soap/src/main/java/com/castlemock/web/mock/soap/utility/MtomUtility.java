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

package com.castlemock.web.mock.soap.utility;

import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The {@link MtomUtility} is a utility class which provides support
 * and utility methods regarding and related to MTOM.
 * @author Karl Dahlgren
 * @since 1.18
 */
public class MtomUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(MtomUtility.class);

    /**
     * The method provides support to extract the main body from an incoming MTOM SOAP request.
     * The method will separate the attachments from the actual body and return only the body.
     * @param requestBody The MTOM request body.
     * @param contentType The content type of the request.
     * @return The extracted main request body
     * @since 1.18
     * @throws IllegalArgumentException In case the extraction of the main request body fails.
     */
    public static String extractMtomBody(final String requestBody, final String contentType){
        InputStream stream = null;
        InputStream bodyInputStream = null;
        ByteArrayOutputStream output = null;
        try {
            stream = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8.name()));
            // Create a new CXF Message
            final Message message = new MessageImpl();
            final Exchange exchange = new ExchangeImpl();
            message.setExchange(exchange);

            // Set both the content type and the content (Request body)
            message.put(Message.CONTENT_TYPE, contentType);
            message.setContent(InputStream.class, stream);

            // Create a deserializer, which will be used to separate the attachments
            // from the request body.
            final AttachmentDeserializer deserializer = new AttachmentDeserializer(message);
            // Initialize the attachments
            deserializer.initializeAttachments();

            // Copy the content (Main request body) and create a string
            bodyInputStream = message.getContent(InputStream.class);
            output = new ByteArrayOutputStream();
            IOUtils.copy(bodyInputStream, output);


            return output.toString();
        } catch (Exception e){
            LOGGER.error("Unable to extract the request body in the MTOM request", e);
            throw new IllegalArgumentException("Unable to extract the request body in the MTOM request");
        } finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close the input stream", e);
                }
            }
            if(bodyInputStream != null){
                try {
                    bodyInputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close the body input stream", e);
                }
            }
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close the output stream", e);
                }
            }

        }

    }

}
