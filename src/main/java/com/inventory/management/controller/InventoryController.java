package com.inventory.management.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/svc")
@Slf4j
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@PostMapping(value = "/publishToAmq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendToAmq(@RequestBody @Valid List<Items> items) {
		log.info("InventoryController : sendToAmq");
		return inventoryService.publishToAmq(items);
	}

	@GetMapping(value = "/getAllItemCategories")
	public List<ItemCategory> getAllItemCategories() {
		log.info("InventoryController : getAllItemCategories");
		return inventoryService.loadItemCategories();

	}
}
