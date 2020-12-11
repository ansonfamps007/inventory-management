package com.inventory.management.service;

import java.util.List;

import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.model.ItemRecords;

public interface InventoryService {

	String saveProductAudit(List<ItemRecords> itemRecords);

	List<ItemCategory> loadItemCategories();

	String publishToAmq(List<Items> items);

	void init();
}
