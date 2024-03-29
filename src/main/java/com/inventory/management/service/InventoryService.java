package com.inventory.management.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.model.ItemRecords;

public interface InventoryService {

	String saveProductAudit(List<ItemRecords> itemRecords);

	List<ItemCategory> loadItemCategories();

	String publishItems(List<Items> items);

	void initCategoryMap();

	String uploadItems(MultipartFile file);
}
