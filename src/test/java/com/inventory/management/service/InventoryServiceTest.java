package com.inventory.management.service;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.repository.ItemCategoryRepo;
import com.inventory.management.repository.ProductAuditRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InventoryServiceTest {

	@Autowired
	private InventoryService inventoryService;

	@MockBean
	private ItemCategoryRepo itemCategoryRepo;
	
	@MockBean
	private ProductAuditRepo productAuditRepo;
	

	@BeforeEach
	public void init() {
		List<ItemCategory> itemCategories = new ArrayList<>();
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setItemCategory("testItem");
		itemCategory.setMaxPrice(100);
		itemCategory.setMinPrice(50);
		itemCategories.add(itemCategory);
		doReturn(itemCategories).when(itemCategoryRepo).findAll();
		doReturn(false).when(productAuditRepo).existsByItemRecordAndItemCategory(Mockito.anyString(), Mockito.anyString());
		this.inventoryService.init();
	}

	@Test
	public void publishToAmqTest() {
		List<Items> itemsList = new ArrayList<>();
		Items items = new Items();
		items.setItemCategory("testItem");
		items.setItemId("testId");
		items.setLocationId("testLocation");
		items.setPrice(100);
		items.setQuantity(123);
		itemsList.add(items);
		String str = inventoryService.publishToAmq(itemsList);
		System.out.println(str);
	}
}
