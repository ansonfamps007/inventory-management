package com.inventory.management.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.inventory.management.model.ItemRecords;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Sender {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void publish(String destination, List<ItemRecords> itemRecords) {
		log.info("sending message='{}' to destination='{}'", itemRecords, destination);
		jmsTemplate.convertAndSend(destination, itemRecords);
	}
}