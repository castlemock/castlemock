package com.castlemock.repository.core.mongodb.event;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.repository.core.mongodb.MongoRepository;
import org.dozer.Mapping;

import java.util.Date;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
public abstract class AbstractEventMongoRepository<T extends AbstractEventMongoRepository.EventDocument, D> extends MongoRepository<T, D, String> {

    public static abstract class EventDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("resourceName")
        private String resourceName;
        @Mapping("startDate")
        private Date startDate;
        @Mapping("endDate")
        private Date endDate;

        public EventDocument() {
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

}
