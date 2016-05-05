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

package com.castlemock.web.mock.soap.web.mvc.command.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The WSDLFileUploadForm is a class that represent a form which contains a multipart files, URL to a specific WSDL
 * file and a boolean value used to indicate if SOAP mocked responses will be generated for each created
 * SOAP operation
 * @author Karl Dahlgren
 * @since 1.0
 * @see javax.wsdl.extensions.soap.SOAPOperation
 * @see SoapMockResponse
 */
public class WSDLFileUploadForm {

    private boolean generateResponse;
    private List<MultipartFile> files;
    private String link;

    /**
     * Returns the multipart files
     * @return The multipart files
     */
    public List<MultipartFile> getFiles() {
        return files;
    }

    /**
     * Set new value to the multipart file list.
     * @param files The new value for the multipart list
     */
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    /**
     * The value represent if one response will be generated upon operation creation.
     * True will generate SOAP mock responses for each SOAP operation created. False will not
     * create any mock responses
     * @return The generate response value
     * @see SoapOperation
     */
    public boolean isGenerateResponse() {
        return generateResponse;
    }

    /**
     * Set a new value to the generate response value
     * @param generateResponse The new value of the generate response, which will response the old one.
     */
    public void setGenerateResponse(boolean generateResponse) {
        this.generateResponse = generateResponse;
    }

    /**
     * The link to the WSDL. This option is used if the user does not upload a WSDL file, but links it instead
     * to an external source
     * @return The link to the WSDL file
     */
    public String getLink() {
        return link;
    }

    /**
     * Set a new value to the link
     * @param link The new value of the link, which will replace the old one
     */
    public void setLink(String link) {
        this.link = link;
    }
}
