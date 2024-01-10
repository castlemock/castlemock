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

package com.castlemock.model.core.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

/**
 * The Event DTO is a DTO (Data transfer object) class for the event class
 * @author Karl Dahlgren
 * @since 1.0
 * @see Event
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = Event.Builder.class)
public abstract class Event {

    @XmlElement
    protected final String id;

    @XmlElement
    protected final String resourceName;

    @XmlElement
    protected final Date startDate;

    @XmlElement
    protected final Date endDate;

    protected Event(final Builder<?> builder){
        this.resourceName = Objects.requireNonNull(builder.resourceName, "resourceName");
        this.id = Objects.requireNonNull(builder.id, "id");
        this.startDate = Objects.requireNonNull(builder.startDate, "startDate");
        this.endDate = Objects.requireNonNull(builder.endDate, "endDate");
    }


    public String getId() {
        return id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @SuppressWarnings("unchecked")
    public static class Builder<B extends Builder<B>> {

        private String id;
        private String resourceName;
        private Date startDate;
        private Date endDate;

        protected Builder() {
        }

        public B id(final String id) {
            this.id = id;
            return (B) this;
        }

        public B resourceName(final String resourceName) {
            this.resourceName = resourceName;
            return (B) this;
        }

        public B startDate(final Date startDate) {
            this.startDate = startDate;
            return (B) this;
        }

        public B endDate(final Date endDate) {
            this.endDate = endDate;
            return (B) this;
        }

    }
}
