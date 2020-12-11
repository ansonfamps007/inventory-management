package com.inventory.management.bean;

import java.util.List;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.inventory.management.model.ItemRecords;

import lombok.extern.slf4j.Slf4j;

/*
 * @Component
 * 
 * @Slf4j public class Receiver {
 * 
 * @JmsListener(destination = "inventory_management.items_queue",
 * containerFactory = "jmsFactory") public void receive(List<ItemRecords>
 * itemRecords) { log.info("received message='{}'", itemRecords); } }
 */