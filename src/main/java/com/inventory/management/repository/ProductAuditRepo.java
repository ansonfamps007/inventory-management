package com.inventory.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.management.model.ItemRecords;

public interface ProductAuditRepo extends MongoRepository<ItemRecords, String> {

	boolean existsByItemRecordAndItemCategory(String itemRecord, String itemCategory);

}
