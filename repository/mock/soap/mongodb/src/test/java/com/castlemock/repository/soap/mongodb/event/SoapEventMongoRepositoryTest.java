package com.castlemock.repository.soap.mongodb.event;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.domain.SoapRequest;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.MONGODB)
public class SoapEventMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    SoapEventMongoRepository soapEventMongoRepository;

    @Test
    public void testSave_WillSaveAllObjectGraph() {
        SoapRequest soapRequest = new SoapRequest();
        soapRequest.setHttpMethod(HttpMethod.DELETE);
        HttpHeader httpHeader1 = new HttpHeader();
        httpHeader1.setName("h1");
        httpHeader1.setValue("v1");
        HttpHeader httpHeader2 = new HttpHeader();
        httpHeader1.setName("h2");
        httpHeader1.setValue("v2");
        soapRequest.setHttpHeaders(Arrays.asList(httpHeader1, httpHeader2));
        SoapEvent soapEvent = new SoapEvent("r1", soapRequest, "pr1", "po1", "op1");
        soapEvent.setEndDate(new Date());
        SoapEvent saved = soapEventMongoRepository.save(soapEvent);

        SoapEvent fromDb = soapEventMongoRepository.findOne(saved.getId());
        assertThat(fromDb.getRequest()).usingRecursiveComparison().isEqualTo(soapRequest);
    }

    @Test
    public void testFindEventsByOperationId() {

        soapEventMongoRepository.save(createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()));
        soapEventMongoRepository.save(createEvent("r2", "pr2", "po2", "opr1", LocalDate.now()));
        soapEventMongoRepository.save(createEvent("r3", "pr3", "po3", "opr2", LocalDate.now()));
        soapEventMongoRepository.save(createEvent("r4", "pr4", "po4", "opr2", LocalDate.now()));

        List<SoapEvent> events = soapEventMongoRepository.findEventsByOperationId("opr1");
        assertThat(events).hasSize(2);
        assertThat(events.get(0).getResourceName()).isEqualTo("r1");
        assertThat(events.get(1).getResourceName()).isEqualTo("r2");
    }

    @Test
    public void testGetAndDeleteOldestEvent() {

        soapEventMongoRepository.save(createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()));
        soapEventMongoRepository.save(createEvent("r2", "pr2", "po2", "opr2", LocalDate.now().minusDays(1)));
        soapEventMongoRepository.save(createEvent("r3", "pr3", "po3", "opr3", LocalDate.now().minusDays(2)));
        SoapEvent oldest = createEvent("r4", "pr4", "po4", "opr4", LocalDate.now().minusDays(3));
        soapEventMongoRepository.save(oldest);

        SoapEvent oldestEventFromMongo = soapEventMongoRepository.getOldestEvent();
        assertThat(oldestEventFromMongo).isEqualToIgnoringGivenFields(oldest, "id");

        SoapEvent deleteOldestEvent = soapEventMongoRepository.deleteOldestEvent();
        assertThat(deleteOldestEvent).isEqualToIgnoringGivenFields(oldest, "id");

        assertThat(soapEventMongoRepository.count()).isEqualTo(3);
    }

    @After
    public void after() {
        mongoOperations.dropCollection("soapEvent");
    }

    // private utility
    private SoapEvent createEvent(final String resourceName, final String projectId,
                                  final String portId, final String operationId, LocalDate startDate) {
        SoapEvent evt = new SoapEvent(resourceName, null, projectId, portId, operationId);
        evt.setStartDate(Date.from(startDate.atStartOfDay().toInstant(ZoneOffset.UTC)));
        evt.setEndDate(new Date());
        return evt;
    }
}