package com.castlemock.repository.core.file.event.model;

import com.castlemock.model.core.Saveable;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "event")
public abstract class EventFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("resourceName")
    private String resourceName;
    @Mapping("startDate")
    private Date startDate;
    @Mapping("endDate")
    private Date endDate;

    protected EventFile() {
    }

    protected EventFile(final Builder<?> builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.resourceName = Objects.requireNonNull(builder.resourceName, "resourceName");
        this.startDate = Objects.requireNonNull(builder.startDate, "startDate");
        this.endDate = Objects.requireNonNull(builder.endDate, "endDate");
    }

    @XmlElement
    @Override
    public String getId() {
        return id;
    }

    @Override
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