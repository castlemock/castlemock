package com.castlemock.repository.core.file.event.model;

import com.castlemock.model.core.Saveable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
public abstract class EventFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String resourceName;
    @XmlElement
    private Date startDate;
    @XmlElement
    private Date endDate;

    protected EventFile() {
    }

    protected EventFile(final Builder<?> builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.resourceName = Objects.requireNonNull(builder.resourceName, "resourceName");
        this.startDate = Objects.requireNonNull(builder.startDate, "startDate");
        this.endDate = Objects.requireNonNull(builder.endDate, "endDate");
    }

    @Override
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

    @SuppressWarnings("unchecked")
    public abstract static class Builder<B extends Builder<B>> {
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