package com.castlemock.core.mock.soap.model.project.domain;

import com.castlemock.core.basis.model.TypeIdentifier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SoapProjectTestBuilder {

    private String id;
    private String name;
    private Date updated;
    private Date created;
    private String description;
    private TypeIdentifier typeIdentifier;
    private List<SoapPort> ports;
    private List<SoapResource> resources;
    private Map<SoapOperationStatus, Integer> statusCount;

    private SoapProjectTestBuilder() {
        this.id = "SOAP PROJECT";
        this.name = "Project name";
        this.description = "Project description";
        this.updated = new Date();
        this.created = new Date();
        this.ports = new CopyOnWriteArrayList<SoapPort>();
        this.resources = new CopyOnWriteArrayList<SoapResource>();
        this.statusCount = new HashMap<SoapOperationStatus, Integer>();

        this.typeIdentifier = new TypeIdentifier() {
            @Override
            public String getType() {
                return "SOAP";
            }

            @Override
            public String getTypeUrl() {
                return "soap";
            }
        };
    }

    public static SoapProjectTestBuilder builder(){
        return new SoapProjectTestBuilder();
    }

    public SoapProjectTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public SoapProjectTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SoapProjectTestBuilder updated(final Date updated) {
        this.updated = updated;
        return this;
    }

    public SoapProjectTestBuilder ports(final List<SoapPort> ports) {
        this.ports = ports;
        return this;
    }

    public SoapProjectTestBuilder created(final Date created) {
        this.created = created;
        return this;
    }

    public SoapProjectTestBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public SoapProjectTestBuilder resources(final List<SoapResource> resources) {
        this.resources = resources;
        return this;
    }

    public SoapProjectTestBuilder typeIdentifier(final TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        return this;
    }

    public SoapProjectTestBuilder statusCount(final Map<SoapOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
        return this;
    }

    public SoapProject build() {
        return SoapProject.builder()
                .created(created)
                .description(description)
                .id(id)
                .name(name)
                .ports(ports)
                .resources(resources)
                .statusCount(statusCount)
                .typeIdentifier(typeIdentifier)
                .updated(updated)
                .build();
    }

}
