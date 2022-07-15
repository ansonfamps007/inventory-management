package com.inventory.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.service.InventoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {

	private static final String TEST_USER_ID = "test_user";

	@MockBean
	private InventoryService inventoryService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAllItemCategories() throws Exception {

		List<ItemCategory> itemCategories = new ArrayList<>();
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setItemCategory("testItem");
		itemCategory.setMaxPrice(100);
		itemCategory.setMinPrice(50);
		itemCategories.add(itemCategory);
		when(inventoryService.loadItemCategories()).thenReturn(itemCategories);
		/*
		 * MvcResult result = mockMvc
		 * .perform(get("/svc/getAllItemCategories").with(user(TEST_USER_ID)).with(csrf(
		 * ))) .andExpect(status().isOk()).andReturn();
		 */
		ObjectMapper mapper = new ObjectMapper();
		final CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class,
				ItemCategory.class);
		/*
		 * final List<ItemCategory> mockList =
		 * mapper.readValue(result.getResponse().getContentAsString(), typeReference);
		 * assertEquals(itemCategories.get(0).getItemCategory(),
		 * mockList.get(0).getItemCategory());
		 */
	}

	@Test
	public void sendToAmq() throws Exception {

		List<Items> itemsList = new ArrayList<>();
		Items items = new Items();
		items.setItemCategory("testItem");
		items.setItemId("testId");
		items.setLocationId("testLocation");
		items.setPrice(100);
		items.setQuantity(123);
		itemsList.add(items);
		when(inventoryService.publishItems(itemsList)).thenReturn("Successfully published to AMQ");
		ObjectMapper mapper = new ObjectMapper();
		/*
		 * mockMvc.perform(post("/svc/publishToAmq").with(user(TEST_USER_ID)).with(csrf(
		 * )).content(mapper.writeValueAsString(itemsList))
		 * .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.
		 * APPLICATION_JSON_VALUE)) .andExpect(status().isOk()).andReturn();
		 */
	}
}
