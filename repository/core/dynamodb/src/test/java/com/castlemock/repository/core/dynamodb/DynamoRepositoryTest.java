package com.castlemock.repository.core.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.repository.core.dynamodb.example.InventoryDynamoRepository;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.castlemock.repository.core.dynamodb.example.InventoryDynamoRepository.Inventory;
import static com.castlemock.repository.core.dynamodb.example.InventoryDynamoRepository.InventoryDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertNotNull;

public class DynamoRepositoryTest extends AbstractDynamoRepositoryTet {

    @Autowired
    private InventoryDynamoRepository inventoryDynamoRepository;

    @Before
    public void setup() {
        super.setup();

        CreateTableRequest createTableRequest =
                dynamoDBMapper.generateCreateTableRequest(InventoryDocument.class)
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSave_DoMappingUsingDozerAndThenSavesDocumentIntoDynamodb() {

        inventoryDynamoRepository.save(new Inventory("Apple Watch", 20.0));
        List<InventoryDocument> allInventory = dynamoDBMapper
                .scan(InventoryDocument.class, new DynamoDBScanExpression());

        assertThat(allInventory).hasSize(1);
        assertThat(allInventory.get(0).getId()).isNotNull();
        assertThat(allInventory.get(0))
                .isEqualToIgnoringGivenFields(new InventoryDocument("Apple Watch", 20.0), "id");
    }

    @Test
    public void testSave_callsCheckType() {
        try {
            inventoryDynamoRepository.save(new Inventory("samsung watch", 20.0));
            fail("should throw exception");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("samsung not allowed");
        }
    }

    @Test
    public void testFindOne_WithInvalidId_ShouldReturnNull() {
        Inventory nullInventory = inventoryDynamoRepository.findOne("invalid id");
        assertThat(nullInventory).isNull();
    }

    @Test
    public void testFindOne_WithValidId_ShouldReturnTheObject() {

        InventoryDocument document = new InventoryDocument("Apple Watch", 20.0);
        dynamoDBMapper.save(document);

        Inventory validInventory = inventoryDynamoRepository.findOne(document.getId());
        assertThat(validInventory)
                .isEqualToComparingFieldByField(document);
    }

    @Test
    public void testFindAll() {
        List<InventoryDocument> list = Stream.of(
                new InventoryDocument("Apple Watch", 20.0),
                new InventoryDocument("Apple TV", 20.0),
                new InventoryDocument("Philips TV", 1.0)
        ).sorted(Comparator.comparing(InventoryDocument::getItem)).collect(Collectors.toList());

        list.forEach(dynamoDBMapper::save);

        List<Inventory> all = inventoryDynamoRepository.findAll();
        assertThat(all).hasSize(3);

        List<Inventory> allSorted =
                all.stream().sorted(Comparator.comparing(Inventory::getItem)).collect(Collectors.toList());

        allSorted.forEach(x -> assertNotNull(x.getId()));
        assertThat(allSorted).usingElementComparatorIgnoringFields("id").isEqualTo(list);
    }

    @Test
    public void testUpdate() {

        Inventory inventory = inventoryDynamoRepository.save(new Inventory("Apple TV", 20.0));

        inventory.setCost(30.0);
        inventoryDynamoRepository.update(inventory.getId(), inventory);

        Inventory fromDynamo = inventoryDynamoRepository.findOne(inventory.getId());
        assertThat(fromDynamo.getCost()).isEqualTo(30.0);
    }

    @Test
    public void testUpdate_InputIdCanBeNull() {

        Inventory inventory = inventoryDynamoRepository.save(new Inventory("Apple TV", 20.0));

        inventory.setCost(60.0);
        inventoryDynamoRepository.update(null, inventory);

        Inventory fromDynamo = inventoryDynamoRepository.findOne(inventory.getId());
        assertThat(fromDynamo.getCost()).isEqualTo(60.0);
    }

    @Test
    public void testCount() {
        dynamoDBMapper.save(new InventoryDocument("Apple Watch", 20.0));
        dynamoDBMapper.save(new InventoryDocument("Apple TV", 20.0));
        dynamoDBMapper.save(new InventoryDocument("Philips TV", 1.0));

        Integer count = inventoryDynamoRepository.count();
        assertThat(count).isEqualTo(3);
    }

    @Test
    public void testDelete() {
        InventoryDocument id1 = new InventoryDocument("Apple Watch", 20.0);
        InventoryDocument toBeDeleted = new InventoryDocument("Apple TV", 20.0);
        InventoryDocument id2 = new InventoryDocument("Philips TV", 1.0);

        List<InventoryDocument> list = Stream.of(id1, id2)
                .sorted(Comparator.comparing(InventoryDocument::getItem)).collect(Collectors.toList());

        dynamoDBMapper.save(id1);
        dynamoDBMapper.save(toBeDeleted);
        dynamoDBMapper.save(id2);

        Inventory deleted = inventoryDynamoRepository.delete(toBeDeleted.getId());
        assertThat(deleted).isEqualToComparingFieldByField(toBeDeleted);

        List<Inventory> all = inventoryDynamoRepository.findAll();
        assertThat(all).hasSize(2);

        List<Inventory> allSorted =
                all.stream().sorted(Comparator.comparing(Inventory::getItem)).collect(Collectors.toList());

        allSorted.forEach(x -> assertNotNull(x.getId()));
        assertThat(allSorted).usingElementComparatorIgnoringFields("id").isEqualTo(list);
    }

    @Test
    public void testDelete_WillNotDeleteIfPassedInvalidId_And_Will_throw_Exception() {
        List<InventoryDocument> list = Stream.of(
                new InventoryDocument("Apple Watch", 20.0),
                new InventoryDocument("Apple TV", 30.0),
                new InventoryDocument("Philips TV", 1.0)
        ).sorted(Comparator.comparing(InventoryDocument::getItem)).collect(Collectors.toList());

        list.forEach(dynamoDBMapper::save);

        try {
            // Note, this behaviour is the same as file-based repository. it is kept as it is for full compliance.
            // However in such case, delete method might return null, or even throws a more fine-grained exception instead.
            inventoryDynamoRepository.delete("invalid id");
            fail("should throw mapping exception");
        } catch (org.dozer.MappingException ex) {
            assertThat(ex.getMessage()).isEqualTo("Source object must not be null");
        }

        List<Inventory> all = inventoryDynamoRepository.findAll();
        assertThat(all).hasSize(3);

        List<Inventory> allSorted =
                all.stream().sorted(Comparator.comparing(Inventory::getItem)).collect(Collectors.toList());

        allSorted.forEach(x -> assertNotNull(x.getId()));
        assertThat(allSorted).usingElementComparatorIgnoringFields("id").isEqualTo(list);
    }

    @Test
    public void testExists_IsTrue() {

        InventoryDocument inventoryDocument = new InventoryDocument("Apple Watch", 20.0);
        dynamoDBMapper.save(inventoryDocument);

        boolean exists = inventoryDynamoRepository.exists(inventoryDocument.getId());
        assertThat(exists).isTrue();
    }

    @Test
    public void testExists_IsFalse() {
        boolean exists = inventoryDynamoRepository.exists("invalid id");
        assertThat(exists).isFalse();
    }

    @After
    public void after() {
        DeleteTableRequest deleteTableRequest =
                dynamoDBMapper.generateDeleteTableRequest(InventoryDocument.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }
}