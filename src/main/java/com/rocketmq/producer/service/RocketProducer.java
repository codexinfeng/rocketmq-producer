package com.rocketmq.producer.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangxianbin
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
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void destroy() {
		if (producer != null) {
			producer.shutdown();
		}
	}

}
