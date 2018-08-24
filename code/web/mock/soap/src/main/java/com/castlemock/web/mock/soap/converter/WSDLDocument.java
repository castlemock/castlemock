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
