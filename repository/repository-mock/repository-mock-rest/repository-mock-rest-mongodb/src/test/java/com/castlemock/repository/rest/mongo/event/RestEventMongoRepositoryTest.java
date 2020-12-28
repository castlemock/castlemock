package com.castlemock.repository.rest.mongo.event;

import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.Profiles;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.MONGODB)
public class RestEventMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private RestEventMongoRepository restEventMongoRepository;

    @Test
    public void testFindEventsByOperationId() {

        restEventMongoRepository.save(createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()));
        restEventMongoRepository.save(createEvent("r2", "pr2", "po2", "opr1", LocalDate.now()));
        restEventMongoRepository.save(createEvent("r3", "pr3", "po3", "opr2", LocalDate.now()));
        restEventMongoRepository.save(createEvent("r4", "pr4", "po4", "opr2", LocalDate.now()));

        List<RestEvent> events = restEventMongoRepository.findEventsByMethodId("opr1");
        assertThat(events).hasSize(2);
        assertThat(events.get(0).getResourceName()).isEqualTo("r1");
        assertThat(events.get(1).getResourceName()).isEqualTo("r2");
    }

    @Test
    public void testGetAndDeleteOldestEvent() {

        restEventMongoRepository.save(createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()));
        restEventMongoRepository.save(createEvent("r2", "pr2", "po2", "opr2", LocalDate.now().minusDays(1)));
        restEventMongoRepository.save(createEvent("r3", "pr3", "po3", "opr3", LocalDate.now().minusDays(2)));
        RestEvent oldest = createEvent("r4", "pr4", "po4", "opr4", LocalDate.now().minusDays(3));
        restEventMongoRepository.save(oldest);

        RestEvent oldestEventFromMongo = restEventMongoRepository.getOldestEvent();
        assertThat(oldestEventFromMongo).isEqualToIgnoringGivenFields(oldest, "id");

        RestEvent deleteOldestEvent = restEventMongoRepository.deleteOldestEvent();
        assertThat(deleteOldestEvent).isEqualToIgnoringGivenFields(oldest, "id");

        assertThat(restEventMongoRepository.count()).isEqualTo(3);
    }

    @After
    public void after() {
        mongoOperations.dropCollection("restEvent");
    }

    // private utility
    private RestEvent createEvent(final String resourceName, final String projectId,
                                  final String applicationId, final String methodId, LocalDate startDate) {
        RestEvent evt = new RestEvent(resourceName, null, projectId, applicationId, "resourceId", methodId);
        evt.setStartDate(Date.from(startDate.atStartOfDay().toInstant(ZoneOffset.UTC)));
        evt.setEndDate(new Date());
        return evt;
    }
}