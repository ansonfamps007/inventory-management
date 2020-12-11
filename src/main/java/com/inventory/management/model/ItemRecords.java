package com.inventory.management.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "ProductAudit")
public class ItemRecords implements Serializable {

	private static final long serialVersionUID = 1L;

	private String itemRecord;

	private String locationId;

	private String itemCategory;
	
	private String itemDetails;

	private double price;

	private int quantity;

	private double minPrice;

	private double maxPrice;
}
