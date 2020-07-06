package com.castlemock.repository.rest.dynamo.event;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.repository.rest.dynamo.AbstractDynamoRepositoryTet;
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

public class RestEventDynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    private RestEventDynamoRepository restEventDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(RestEventDynamoRepository.RestEventDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void testFindEventsByOperationId() {

        List<RestEvent> list1 = Arrays.asList(
                createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()),
                createEvent("r2", "pr2", "po2", "opr1", LocalDate.now())
        );

        List<RestEvent> list2 = Arrays.asList(
                createEvent("r3", "pr3", "po3", "opr2", LocalDate.now()),
                createEvent("r4", "pr4", "po4", "opr2", LocalDate.now())
        );

        list1.forEach(restEventDynamoRepository::save);
        list2.forEach(restEventDynamoRepository::save);

        List<RestEvent> events = restEventDynamoRepository.findEventsByMethodId("opr1");
        assertThat(events).hasSize(2);

        List<RestEvent> sortedList = list1.stream()
                .sorted(Comparator.comparing(RestEvent::getResourceName)).collect(Collectors.toList());

        List<RestEvent> eventsSorted = events.stream()
                .sorted(Comparator.comparing(RestEvent::getResourceName)).collect(Collectors.toList());

        assertThat(eventsSorted).usingDefaultElementComparator().isEqualTo(sortedList);
    }

    @Test
    public void testGetAndDeleteOldestEvent() {

        restEventDynamoRepository.save(createEvent("r1", "pr1", "po1", "opr1", LocalDate.now()));
        restEventDynamoRepository.save(createEvent("r2", "pr2", "po2", "opr2", LocalDate.now().minusDays(1)));
        restEventDynamoRepository.save(createEvent("r3", "pr3", "po3", "opr3", LocalDate.now().minusDays(2)));
        RestEvent oldest = createEvent("r4", "pr4", "po4", "opr4", LocalDate.now().minusDays(3));
        restEventDynamoRepository.save(oldest);

        RestEvent oldestEventFromMongo = restEventDynamoRepository.getOldestEvent();
        assertThat(oldestEventFromMongo).isEqualToIgnoringGivenFields(oldest, "id");

        RestEvent deleteOldestEvent = restEventDynamoRepository.deleteOldestEvent();
        assertThat(deleteOldestEvent).isEqualToIgnoringGivenFields(oldest, "id");

        assertThat(restEventDynamoRepository.count()).isEqualTo(3);
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(RestEventDynamoRepository.RestEventDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
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