package com.inventory.management.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

	private final InventoryService inventoryService;

	@Autowired
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@PostMapping(value = "/publishItems", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Publish Items")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public String publishItems(@RequestBody @Valid List<Items> items) {
		log.info("InventoryController : publishItems");
		return inventoryService.publishItems(items);
	}

	@PostMapping(value = "/uploadItems", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "Upload Items")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public String uploadItems(@RequestParam("file") MultipartFile file) {
		log.info("InventoryController : uploadItems");
		return inventoryService.uploadItems(file);
	}

	@GetMapping(value = "/getAllItemCategories", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch All Item Categories")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public List<ItemCategory> getAllItemCategories() {
		log.info("InventoryController : getAllItemCategories");
		return inventoryService.loadItemCategories();

	}
}
