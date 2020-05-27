package com.rocketmq.producer.service;

import javax.annotation.Resource;

import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rocketmq.producer.model.OrderInfo;

/**
 * @author zhangxianbin
 *
 *         2020年5月26日
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private RocketProducer rocketProducer;

	@Override
	public String createOrder(OrderInfo info) {
		String body = JSONObject.toJSONString(info);
		Message msg = new Message();
		msg.setTopic("CREATE_ORDER");
		msg.setBody(body.getBytes());
		rocketProducer.sendMsg(msg);
		return null;
	}

}
