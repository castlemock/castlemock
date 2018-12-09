package com.castlemock.repository.core.mongodb;

import com.castlemock.repository.core.mongodb.example.InventoryMongoRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.castlemock.repository.core.mongodb.example.InventoryMongoRepository.Inventory;
import static com.castlemock.repository.core.mongodb.example.InventoryMongoRepository.InventoryDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataMongoTest
@RunWith(SpringRunner.class)
public class MongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private InventoryMongoRepository inventoryMongoRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSave_DoMappingUsingDozerAndThenSavesDocumentIntoMongodb() {

        inventoryMongoRepository.save(new Inventory("Apple Watch", 20.0));
        List<InventoryDocument> allInventory = mongoOperations.findAll(InventoryDocument.class);

        assertThat(allInventory).hasSize(1);
        assertThat(allInventory.get(0).getId()).isNotNull();
        assertThat(allInventory.get(0))
                .isEqualToIgnoringGivenFields(new InventoryDocument("Apple Watch", 20.0), "id");
    }

    @Test
    public void testSave_callsCheckType() {
        try {
            inventoryMongoRepository.save(new Inventory("samsung watch", 20.0));
            fail("should throw exception");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("samsung not allowed");
        }
    }

    @Test
    public void testFindOne_WithInvalidId_ShouldReturnNull() {
        Inventory nullInventory = inventoryMongoRepository.findOne("invalid id");
        assertThat(nullInventory).isNull();
    }

    @Test
    public void testFindOne_WithValidId_ShouldReturnTheObject() {

        InventoryDocument document = new InventoryDocument("Apple Watch", 20.0);
        mongoOperations.save(document);

        Inventory validInventory = inventoryMongoRepository.findOne(document.getId());
        assertThat(validInventory)
                .isEqualToComparingFieldByField(document);
    }

    @Test
    public void testFindAll() {
        mongoOperations.save(new InventoryDocument("Apple Watch", 20.0));
        mongoOperations.save(new InventoryDocument("Apple TV", 20.0));
        mongoOperations.save(new InventoryDocument("Philips TV", 1.0));

        List<Inventory> all = inventoryMongoRepository.findAll();
        assertThat(all).hasSize(3);
        assertThat(all.get(2).getCost()).isEqualTo(1.0);
    }

    @Test
    public void testUpdate() {

        Inventory inventory = inventoryMongoRepository.save(new Inventory("Apple TV", 20.0));

        inventory.setCost(30.0);
        inventoryMongoRepository.update(inventory.getId(), inventory);

        Inventory fromMongo = inventoryMongoRepository.findOne(inventory.getId());
        assertThat(fromMongo.getCost()).isEqualTo(30.0);
    }

    @Test
    public void testUpdate_InputIdCanBeNull() {

        Inventory inventory = inventoryMongoRepository.save(new Inventory("Apple TV", 20.0));

        inventory.setCost(60.0);
        inventoryMongoRepository.update(null, inventory);

        Inventory fromMongo = inventoryMongoRepository.findOne(inventory.getId());
        assertThat(fromMongo.getCost()).isEqualTo(60.0);
    }

    @Test
    public void testCount() {
        mongoOperations.save(new InventoryDocument("Apple Watch", 20.0));
        mongoOperations.save(new InventoryDocument("Apple TV", 20.0));
        mongoOperations.save(new InventoryDocument("Philips TV", 1.0));

        Integer count = inventoryMongoRepository.count();
        assertThat(count).isEqualTo(3);
    }

    @Test
    public void testDelete() {
        mongoOperations.save(new InventoryDocument("Apple Watch", 20.0));
        InventoryDocument toBeDeleted = new InventoryDocument("Apple TV", 20.0);
        mongoOperations.save(toBeDeleted);
        mongoOperations.save(new InventoryDocument("Philips TV", 1.0));

        Inventory deleted = inventoryMongoRepository.delete(toBeDeleted.getId());
        assertThat(deleted).isEqualToComparingFieldByField(toBeDeleted);

        List<Inventory> all = inventoryMongoRepository.findAll();
        assertThat(all).hasSize(2);
        assertThat(all.get(0).getCost()).isEqualTo(20.0);
        assertThat(all.get(1).getCost()).isEqualTo(1.0);
    }

    @Test
    public void testDelete_WillNotDeleteIfPassedInvalidId_And_Will_throw_Exception() {
        mongoOperations.save(new InventoryDocument("Apple Watch", 20.0));
        mongoOperations.save(new InventoryDocument("Apple TV", 30.0));
        mongoOperations.save(new InventoryDocument("Philips TV", 1.0));

        try {
            // Note, this behaviour is the same as file-based repository. it is kept as it is for full compliance.
            // However in such case, delete method might return null, or even throws a more fine-grained exception instead.
            inventoryMongoRepository.delete("invalid id");
            fail("should throw mapping exception");
        } catch (org.dozer.MappingException ex) {
            assertThat(ex.getMessage()).isEqualTo("Source object must not be null");
        }

        List<Inventory> all = inventoryMongoRepository.findAll();
        assertThat(all).hasSize(3);
        assertThat(all.get(0).getCost()).isEqualTo(20.0);
        assertThat(all.get(2).getCost()).isEqualTo(1.0);
    }

    @Test
    public void testExists_IsTrue() {

        InventoryDocument inventoryDocument = new InventoryDocument("Apple Watch", 20.0);
        mongoOperations.save(inventoryDocument);

        boolean exists = inventoryMongoRepository.exists(inventoryDocument.getId());
        assertThat(exists).isTrue();
    }

    @Test
    public void testExists_IsFalse() {
        boolean exists = inventoryMongoRepository.exists("invalid id");
        assertThat(exists).isFalse();
    }

    @After
    public void after() {
        mongoOperations.dropCollection(InventoryDocument.class);
    }
}