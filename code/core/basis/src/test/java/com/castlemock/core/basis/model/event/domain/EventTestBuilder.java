package com.castlemock.core.basis.model.event.domain;

import com.castlemock.core.basis.model.TypeIdentifier;

import java.util.Date;
import java.util.UUID;

public final class EventTestBuilder {

    private String id;
    private String resourceName;
    private Date startDate;
    private Date endDate;
    private TypeIdentifier typeIdentifier;
    private String resourceLink;

    private EventTestBuilder(){
        this.id = UUID.randomUUID().toString();
        this.startDate = new Date();
        this.endDate = new Date();
    }

    public static EventTestBuilder builder(){
        return new EventTestBuilder();
    }

    public EventTestBuilder id(final String id){
        this.id = id;
        return this;
    }

    public EventTestBuilder resourceName(final String resourceName){
        this.resourceName = resourceName;
        return this;
    }

    public EventTestBuilder startDate(final Date startDate){
        this.startDate = startDate;
        return this;
    }

    public EventTestBuilder endDate(final Date endDate){
        this.endDate = endDate;
        return this;
    }

    public EventTestBuilder typeIdentifier(final TypeIdentifier typeIdentifier){
        this.typeIdentifier = typeIdentifier;
        return this;
    }

    public EventTestBuilder resourceLink(final String resourceLink){
        this.resourceLink = resourceLink;
        return this;
    }

    public Event build(){
        final Event event = new Event();
        event.setEndDate(endDate);
        event.setId(id);
        event.setResourceLink(resourceLink);
        event.setResourceName(resourceName);
        event.setStartDate(startDate);
        event.setTypeIdentifier(typeIdentifier);
        return event;
    }

}
