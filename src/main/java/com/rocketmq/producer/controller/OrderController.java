package com.rocketmq.producer.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketmq.producer.model.OrderInfo;
import com.rocketmq.producer.service.OrderService;

/**
 *
 *         2020年5月26日
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Resource
	private OrderService orderService;

	@PostMapping(value = { "/createOrder" })
	public String createOrder(@RequestBody OrderInfo info) {
		orderService.createOrder(info);
		return "success";
	}
	
	@PostMapping(value = { "/createTransactionOrder" })
	public String createTransactionOrder(OrderInfo info) {
		orderService.createTransactionMsg();
		return "success";
	}
	@PostMapping(value = { "/createAsyncOrder" })
	public String createAsyncOrder(OrderInfo info) {
		orderService.createAsyncOrder(info);
		return "success";
	}
}
