package com.castlemock.repository.core.file.event;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.repository.core.file.FileRepository;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

public abstract class AbstractEventFileRepository<T extends AbstractEventFileRepository.EventFile, D> extends FileRepository<T, D, String> {

    @XmlRootElement(name = "event")
    public static abstract class EventFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("resourceName")
        private String resourceName;
        @Mapping("startDate")
        private Date startDate;
        @Mapping("endDate")
        private Date endDate;

        public EventFile() {
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
    }

}
