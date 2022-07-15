package com.inventory.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ITEM_CATEGORY")
@Data
public class ItemCategory {

	@Id
	@Column(name = "ITEM_CATEGOARY")
	private String itemCategory;

	@Column(name = "MIN_PRIZE")
	private double minPrice;

	@Column(name = "MAX_PRIZE")
	private double maxPrice;

}
