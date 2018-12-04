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
    public void testSaveMethod_DoMappingUsingDozerAndThenSavesDocumentIntoMongodb() {

        inventoryMongoRepository.save(new Inventory("Apple Watch", 20.0));
        List<InventoryDocument> allInventory = mongoOperations.findAll(InventoryDocument.class);

        assertThat(allInventory).hasSize(1);
        assertThat(allInventory.get(0).getId()).isNotNull();
        assertThat(allInventory.get(0))
                .isEqualToIgnoringGivenFields(new InventoryDocument("Apple Watch", 20.0), "id");
    }

    @After
    public void after() {
        mongoOperations.dropCollection(InventoryDocument.class);
    }
}