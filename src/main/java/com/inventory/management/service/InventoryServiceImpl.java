package com.inventory.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.inventory.management.bean.Items;
import com.inventory.management.bean.Sender;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.model.ItemRecords;
import com.inventory.management.repository.ItemCategoryRepo;
import com.inventory.management.repository.ProductAuditRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private ProductAuditRepo productAuditRepo;

	@Autowired
	private ItemCategoryRepo itemCategoryRepo;

	@Autowired
	private Sender sender;

	private Map<String, ItemCategory> categoryMap = new HashMap<>();

	@Override
	@PostConstruct
	public void init() {
		if (categoryMap.isEmpty()) {
			categoryMap = loadItemCategories().stream()
					.collect(Collectors.toMap(ItemCategory::getItemCategory, Function.identity()));
			log.debug("-- Category Map Initialized --{}", categoryMap);
		}
	}

	@Override
	public List<ItemCategory> loadItemCategories() {
		return itemCategoryRepo.findAll();
	}

	@Override
	public String publishToAmq(List<Items> items) {
		List<ItemRecords> itemRecords = new ArrayList<>();
		if (!CollectionUtils.isEmpty(items)) {
			items.forEach(item -> {
				if (!validate(item) && !checkDuplicate(item)) {
					ItemCategory itemCategory = categoryMap.get(item.getItemCategory());
					System.out.println("categoryMap--->"+ categoryMap);
					itemRecords.add(ItemRecords.builder().itemRecord(item.getItemId())
							.itemCategory(item.getItemCategory()).locationId(item.getLocationId())
							.itemDetails(item.getItemDetails()).price(item.getPrice()).quantity(item.getQuantity())
							.minPrice(itemCategory.getMinPrice()).maxPrice(itemCategory.getMaxPrice()).build());
				}
			});
		}

		sender.publish("inventory_management.items_queue", itemRecords);
		return saveProductAudit(itemRecords);
	}

	@Override
	public String saveProductAudit(List<ItemRecords> itemRecords) {
		productAuditRepo.saveAll(itemRecords);
		return "Successfully published to AMQ";
	}

	private boolean checkDuplicate(Items item) {
		return productAuditRepo.existsByItemRecordAndItemCategory(item.getItemId(), item.getItemCategory());
	}

	private static boolean validate(Items item) {
		boolean flag = false;
		if (null == item.getItemId() || item.getItemId().isBlank()) {
			log.error("Item Id is null or empty ! {}", item);
			flag = true;
		} else if (null == item.getLocationId() || item.getLocationId().isBlank()) {
			log.error("Location Id is null or empty ! {}", item);
			flag = true;
		}
		return flag;
	}

	/*@JmsListener(destination = "ent.inventory_management::inventory_management.items_queue")
	public void receive(String message) {
		System.out.println("received message='{}'" + message);
		log.info("received message='{}'", message);
	}*/

}
