package com.castlemock.repository.core.mongodb.example;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.repository.core.mongodb.MongoRepository;
import org.dozer.Mapping;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.castlemock.repository.core.mongodb.example.InventoryMongoRepository.Inventory;
import static com.castlemock.repository.core.mongodb.example.InventoryMongoRepository.InventoryDocument;

@Repository
public class InventoryMongoRepository extends MongoRepository<InventoryDocument, Inventory, String> {

    @Override
    protected void checkType(InventoryDocument type) {
        if (type.item.contains("samsung")){
            throw new IllegalArgumentException("samsung not allowed");
        }
    }

    @Override
    public List<Inventory> search(SearchQuery query) {
        return null;
    }

    public static class InventoryDocument implements Saveable<String> {
        @Mapping("id")
        String id;
        @Mapping("item")
        String item;
        @Mapping("cost")
        Double cost;

        public InventoryDocument() {
        }

        public InventoryDocument(String item, Double cost) {
            this.item = item;
            this.cost = cost;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "InventoryDocument{" +
                    "id='" + id + '\'' +
                    ", item='" + item + '\'' +
                    ", cost=" + cost +
                    '}';
        }
    }

    public static class Inventory {
        String id;
        String item;
        Double cost;

        public Inventory() {
        }

        public Inventory(String item, Double cost) {
            this.item = item;
            this.cost = cost;
        }

        public String getId() {
            return id;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public Double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "Inventory{" +
                    "id='" + id + '\'' +
                    ", item='" + item + '\'' +
                    ", cost=" + cost +
                    '}';
        }
    }

}
