package com.inventory.management.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Items {

	@NotNull
	@NotEmpty
	private String itemId;

	@NotBlank
	@NotEmpty
	private String itemCategory;

	@NotBlank
	private String locationId;

	private int quantity;

	private double price;

	@NotBlank
	private String bookTitle;

	@NotBlank
	private String bookAuthor;

	@NotBlank
	private String bookCategory;

	@NotBlank
	private String bookLanguage;

	@NotBlank
	private String bookDescription;

}