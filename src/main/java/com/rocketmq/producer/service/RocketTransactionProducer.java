package com.rocketmq.producer.service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rocketmq.producer.data.OrderData;
import com.rocketmq.producer.model.OrderInfo;

/**
 *
 *         2020年5月28日
 */
@Slf4j
@Service
public class RocketTransactionProducer {
	@Value("${rocketmq.transaction.group}")
	private String producerGroup;

	@Value("${rocketmq.name.addr}")
	private String nameAddr;

	private TransactionMQProducer transactionProducer;

	private ExecutorService executroService;

	@PostConstruct
	public void init() {
		transactionProducer = new TransactionMQProducer(producerGroup);
		executroService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName("client-transaction-msg-check-thread");
						return thread;
					}
				});
		transactionProducer.setExecutorService(executroService);
		transactionProducer.setTransactionListener(new TransactionListenerImpl());
		transactionProducer.setNamesrvAddr(nameAddr);
		try {
			transactionProducer.start();
		} catch (MQClientException e) {
			log.error("transaction producer start failed ", e);
		}

	}

	public void sendTransactionMsg() {
		List<OrderInfo> orderList = OrderData.orderList;
		try {
			for (int i = 0; i < orderList.size(); i++) {
				Message message = new Message();
				String body = JSONObject.toJSONString(orderList.get(i));
				message.setTopic("CREATE_ORDER");
				message.setBody(body.getBytes(RemotingHelper.DEFAULT_CHARSET));
				message.setKeys("Key" + i);
				SendResult result = transactionProducer.sendMessageInTransaction(message, null);
                log.info("message result {}",result);
			}
		} catch (Exception e) {
			log.error("transaction producer send message failed", e);
		}
	}

}
