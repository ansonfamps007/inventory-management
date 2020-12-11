package com.inventory.management.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.inventory.management.model.ItemCategory;

public interface ItemCategoryRepo extends CrudRepository<ItemCategory, String> {

	List<ItemCategory> findAll();

}
