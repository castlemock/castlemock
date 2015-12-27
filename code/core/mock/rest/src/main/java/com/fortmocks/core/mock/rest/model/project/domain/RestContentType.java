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

package com.fortmocks.core.mock.rest.model.project.domain;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public enum RestContentType {

    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    TEXT_XML("text/xml"),
    TEXT_PLAIN("text/plain"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    MULTIPART_MIXED("multipart/mixed");

    private String contentType;

    private RestContentType(final String contentType){
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
