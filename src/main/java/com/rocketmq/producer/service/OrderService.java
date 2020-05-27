package com.rocketmq.producer.service;

import com.rocketmq.producer.model.OrderInfo;

/**
 * @author zhangxianbin
 *
 *  2020年5月26日
 */
public interface OrderService {

	
	String createOrder(OrderInfo info);
}


