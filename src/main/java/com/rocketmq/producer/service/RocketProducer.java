package com.rocketmq.producer.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rocketmq.producer.data.OrderData;
import com.rocketmq.producer.model.OrderInfo;

/**
 *
 *         2020年5月26日
 */
@Service
@Slf4j
public class RocketProducer {

	@Value("${rocketmq.producer.group}")
	private String producerGroup;

	@Value("${rocketmq.name.addr}")
	private String nameAddr;

	private DefaultMQProducer producer;

	@PostConstruct
	public void initProducer() {
		producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(nameAddr);
		try {
			producer.start();
		} catch (MQClientException e) {
			log.error("rocketmq producer connect {} failed", nameAddr, e);
		}
	}

	public void sendMsg(Message msg) {
		try {
			SendResult result = producer.send(msg);
			log.info("result info:" + result);
		} catch (Exception e) {
			log.error("Send Message failed message {}", msg, e);
		}
	}

	public void sendAsynMsg(Message msg) {
		try {
			producer.send(msg, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {

					log.info("send async msg success,threadName = {},msgId = {} ", Thread.currentThread().getName(),
							sendResult.getMsgId());
				}

				@Override
				public void onException(Throwable e) {
					log.error("send async msg failed call back exceptino threadName = {},msg {}", Thread
							.currentThread().getName(), msg, e);
				}

			});
		} catch (Exception e) {
			log.error("send async msg failed  {}", msg, e);
		}
	}

	public void sendOrderMsg() {
		List<OrderInfo> orderList = OrderData.orderList;
		try {
			for (int i = 0; i < orderList.size(); i++) {
				Message msg = new Message("CREATE_SORT_ORDER", "KEY" + i, orderList.get(i).toString().getBytes());
				SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
					// 选择要发送的队列
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						// 入参传递过来,根据Id取余,相同id一定到相同的queue
						Long id = (Long) arg;
						long index = id % mqs.size();
						return mqs.get((int) index);
					}
				}, Long.valueOf(orderList.get(i).getOrderId()));
				log.info("SendResult status:{},queueId:{},body:{} ", sendResult.getSendStatus(), sendResult
						.getMessageQueue().getQueueId(), orderList.get(i).toString());
			}
		} catch (Exception e) {
			log.error("send order message failed ", e);
		}
	}

	// 发送延迟消息,检查某一操作是否执行
	public void sendDelayMsg(Message msg) {
		try {
			// 先保存,3等级为10s,10s后才会被保存
			msg.setDelayTimeLevel(3);
			SendResult result = producer.send(msg);
			log.info("result info {},time:{}", result, System.currentTimeMillis());
		} catch (Exception e) {
			log.error("Send Message failed message {}", msg, e);
		}
	}

	@PreDestroy
	public void destroy() {
		if (producer != null) {
			producer.shutdown();
		}
	}

}
