package com.rocketmq.producer.service;

import com.rocketmq.producer.model.OrderInfo;

/**
 *
 *  2020年5月26日
 */
public interface OrderService {

	
	String createOrder(OrderInfo info);
	
	String createAsyncOrder(OrderInfo info);
	
	String createTransactionMsg();
}


