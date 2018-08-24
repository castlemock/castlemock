package com.castlemock.web.mock.soap.converter;

import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;

import java.util.Set;

public final class SoapPortConverterResult {

    private final String name;
    private final String definition;
    private final Set<SoapPort> ports;
    private final SoapResourceType resourceType;


    private SoapPortConverterResult(final Builder builder) {
        this.name = builder.name;
        this.definition = builder.definition;
        this.ports = builder.ports;
        this.resourceType = builder.resourceType;
    }

    public String getName() {
        return name;
    }

    public String getDefinition() {
        return definition;
    }

    public Set<SoapPort> getPorts() {
        return ports;
    }

    public SoapResourceType getResourceType() {
        return resourceType;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String definition;
        private Set<SoapPort> ports;
        private SoapResourceType resourceType;

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder definition(final String definition){
            this.definition = definition;
            return this;
        }

        public Builder ports(final Set<SoapPort> ports){
            this.ports = ports;
            return this;
        }

        public Builder resourceType(final SoapResourceType resourceType){
            this.resourceType = resourceType;
            return this;
        }

        public SoapPortConverterResult build(){
            return new SoapPortConverterResult(this);
        }

    }
}
