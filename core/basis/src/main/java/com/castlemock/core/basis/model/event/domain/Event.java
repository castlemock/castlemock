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

package com.castlemock.core.basis.model.event.domain;

import com.castlemock.core.basis.model.TypeIdentifiable;
import com.castlemock.core.basis.model.TypeIdentifier;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * The Event DTO is a DTO (Data transfer object) class for the event class
 * @author Karl Dahlgren
 * @since 1.0
 * @see Event
 */
@XmlRootElement
public class Event implements TypeIdentifiable {

    protected String id;
    protected String resourceName;
    protected Date startDate;
    protected Date endDate;
    protected TypeIdentifier typeIdentifier;
    protected String resourceLink;

    /**
     * The default constructor for the event DTO
     */
    public Event(){
        // Empty constructor
    }

    /**
     * The default constructor for the event DTO
     */
    public Event(final String resourceName){
        this.resourceName = resourceName;
        setStartDate(new Date());
    }

    /**
     * The constructor provides the functionality to initialize a new event DTO based on another event DTO
     * @param eventDto The event DTO that the new event DTO is going to based on
     */
    public Event(final Event eventDto){
        this.resourceName = eventDto.getResourceName();
        this.resourceLink = eventDto.getResourceLink();
        this.id = eventDto.getId();
        this.startDate = eventDto.getStartDate();
        this.endDate = eventDto.getEndDate();
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @XmlElement
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlElement
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    @XmlElement
    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    @Override
    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
    }

    @XmlElement
    public String getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }
}
