package com.castlemock.repository.soap.dynamodb.event;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.domain.SoapRequest;
import com.castlemock.repository.soap.dynamodb.AbstractDynamoRepositoryTet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SoapEventDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    SoapEventDynamoRepository soapEventDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(SoapEventDynamoRepository.SoapEventDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

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
        SoapEvent saved = soapEventDynamoRepository.save(soapEvent);

        SoapEvent fromDb = soapEventDynamoRepository.findOne(saved.getId());
        assertThat(fromDb.getRequest()).usingRecursiveComparison().isEqualTo(soapRequest);
    }

    @Test
    public void testFindEventsByOperationId() {

        List<SoapEvent> list1 = Arrays.asList(
                createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()),
                createEvent("r2", "pr2", "po2", "opr1", LocalDate.now())
        );

        List<SoapEvent> list2 = Arrays.asList(
                createEvent("r3", "pr3", "po3", "opr2", LocalDate.now()),
                createEvent("r4", "pr4", "po4", "opr2", LocalDate.now())
        );

        list1.forEach(soapEventDynamoRepository::save);
        list2.forEach(soapEventDynamoRepository::save);

        List<SoapEvent> events = soapEventDynamoRepository.findEventsByOperationId("opr1");
        assertThat(events).hasSize(2);

        List<SoapEvent> sortedList = list1.stream()
                .sorted(Comparator.comparing(SoapEvent::getResourceName)).collect(Collectors.toList());

        List<SoapEvent> eventsSorted = events.stream()
                .sorted(Comparator.comparing(SoapEvent::getResourceName)).collect(Collectors.toList());

        assertThat(eventsSorted).usingElementComparatorIgnoringFields("id").isEqualTo(sortedList);
    }

    @Test
    public void testGetAndDeleteOldestEvent() {

        soapEventDynamoRepository.save(createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()));
        soapEventDynamoRepository.save(createEvent("r2", "pr2", "po2", "opr2", LocalDate.now().minusDays(1)));
        soapEventDynamoRepository.save(createEvent("r3", "pr3", "po3", "opr3", LocalDate.now().minusDays(2)));
        SoapEvent oldest = createEvent("r4", "pr4", "po4", "opr4", LocalDate.now().minusDays(3));
        soapEventDynamoRepository.save(oldest);

        SoapEvent oldestEventFromDynamo = soapEventDynamoRepository.getOldestEvent();
        assertThat(oldestEventFromDynamo).isEqualToIgnoringGivenFields(oldest, "id");

        SoapEvent deleteOldestEvent = soapEventDynamoRepository.deleteOldestEvent();
        assertThat(deleteOldestEvent).isEqualToIgnoringGivenFields(oldest, "id");

        assertThat(soapEventDynamoRepository.count()).isEqualTo(3);
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(SoapEventDynamoRepository.SoapEventDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
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