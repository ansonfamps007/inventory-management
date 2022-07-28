package com.inventory.management.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.management.bean.Items;
import com.inventory.management.model.ItemCategory;
import com.inventory.management.model.ItemRecords;
import com.inventory.management.repository.ItemCategoryRepo;
import com.inventory.management.repository.ProductAuditRepo;
import com.inventory.management.util.ApiConstants;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

	@Value("${app.msg-broker-url}")
	private String MSG_BROKER_URL;

	@Autowired
	private ProductAuditRepo productAuditRepo;

	@Autowired
	private ItemCategoryRepo itemCategoryRepo;

	private static HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(20)).build();

	private Map<String, ItemCategory> categoryMap = new HashMap<>();

	@Override
	@PostConstruct
	public void initCategoryMap() {
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
	public String publishItems(List<Items> items) {
		List<ItemRecords> itemRecords = new ArrayList<>();
		if (!CollectionUtils.isEmpty(items)) {
			items.forEach(item -> {
				if (!validate(item) && !checkDuplicate(item)) {
					if (categoryMap.isEmpty()) {
						initCategoryMap();
					}
					ItemCategory itemCategory = categoryMap.get(item.getItemCategory().toLowerCase());
					log.debug("categoryMap--->" + categoryMap);

					ItemRecords itemRecord = ItemRecords.builder().itemRecord(item.getItemId())
							.itemCategory(item.getItemCategory()).locationId(item.getLocationId())
							.itemDetails(item.getBookTitle()).price(item.getPrice()).quantity(item.getQuantity())
							.minPrice(itemCategory.getMinPrice()).maxPrice(itemCategory.getMaxPrice()).build();

					ObjectMapper mapper = new ObjectMapper();
					try {
						StringBuilder uri = new StringBuilder(MSG_BROKER_URL).append(ApiConstants.TOPIC);
						HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri.toString()))
								.POST(BodyPublishers.ofString(mapper.writeValueAsString(item))).build();

						CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
								HttpResponse.BodyHandlers.ofString());

						String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

						System.out.println("Completable response--->" + result);
					} catch (IOException | URISyntaxException | InterruptedException | ExecutionException
							| TimeoutException e) {
						log.debug("publishItems failed - " + e.getMessage());
					}
					itemRecords.add(itemRecord);
				}
			});
		}

		return saveProductAudit(itemRecords);
	}

	@Override
	public String saveProductAudit(List<ItemRecords> itemRecords) {
		productAuditRepo.saveAll(itemRecords);
		return "Successfully published to Kafka";
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

	@Override
	public String uploadItems(MultipartFile file) {
		Reader reader;
		try {
			reader = new InputStreamReader(file.getInputStream());
			List<Items> items = new CsvToBeanBuilder<Items>(reader).withType(Items.class).build().parse();
			publishItems(items);

		} catch (IOException e) {
			log.error("CSV read failed ! {}", e.getMessage());
		}

		return "Sucessfully Uploaded !";
	}
}
