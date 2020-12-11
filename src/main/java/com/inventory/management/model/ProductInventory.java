package com.inventory.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "PRODUCT_INVENTORY", schema = "SERV_USER")
public class ProductInventory {

	@Id
	@Column(name = "ITEM_ID")
	private String itemId;

	@Column(name = "LOCATION_ID")
	private String locationId;

	@Column(name = "ITEM_CATEGORY")
	private String itemCategory;

	@Column(name = "PRICE")
	private float price;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "MIN_PRICE")
	private float minPrice;

	@Column(name = "MAX_PRICE")
	private float maxPrice;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(final String itemId) {
		this.itemId = itemId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(final String locationId) {
		this.locationId = locationId;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(final String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(final float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(final float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(final float maxPrice) {
		this.maxPrice = maxPrice;
	}

}
