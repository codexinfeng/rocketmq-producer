package com.rocketmq.producer.service;

import javax.annotation.Resource;

import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rocketmq.producer.model.OrderInfo;

/**
 *
 * 2020年5月26日
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private RocketProducer rocketProducer;
	
	@Resource
	private RocketTransactionProducer rocketTransactionProducer;

	@Override
	public String createOrder(OrderInfo info) {
		String body = JSONObject.toJSONString(info);
		Message msg = new Message();
		msg.setTopic("CREATE_ORDER");
		msg.setBody(body.getBytes());
		rocketProducer.sendMsg(msg);
		return null;
	}

	@Override
	public String createAsyncOrder(OrderInfo info) {
		String body = JSONObject.toJSONString(info);
		Message msg = new Message();
		msg.setTopic("CREATE_ORDER");
		msg.setBody(body.getBytes());
		msg.setKeys("ORDER_ID_001");
		rocketProducer.sendAsynMsg(msg);
		return null;
	}

	@Override
	public String createTransactionMsg() {
		rocketTransactionProducer.sendTransactionMsg();
		return null;
	}

}
