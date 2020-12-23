/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.mock.soap.converter;

import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import org.w3c.dom.Document;

public final class WSDLDocument {

    private final Document document;
    private final SoapResourceType resourceType;

    private WSDLDocument(final Builder builder){
        this.document = builder.document;
        this.resourceType = builder.resourceType;
    }

    public Document getDocument() {
        return document;
    }

    public SoapResourceType getResourceType() {
        return resourceType;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private Document document;
        private SoapResourceType resourceType;

        public Builder document(final Document document){
            this.document = document;
            return this;
        }

        public Builder definition(final SoapResourceType resourceType){
            this.resourceType = resourceType;
            return this;
        }

        public WSDLDocument build(){
            return new WSDLDocument(this);
        }

    }
}
