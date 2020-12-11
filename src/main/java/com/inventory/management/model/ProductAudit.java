package com.inventory.management.model;

import lombok.Data;

@Data
public class ProductAudit {

	private String id;

	private String executingNode;

	private String lastRunTs;

	private ItemRecords itemRecords;
}
