package com.rocketmq.producer.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 *
 *         2020年5月28日
 */
@Slf4j
public class TransactionListenerImpl implements TransactionListener {

	private AtomicInteger transactionIndex = new AtomicInteger(0);
	private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<String, Integer>();

	@Override
	public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		log.info("execute transcation msg threadname:{}, {}", Thread.currentThread().getName(), msg);

		// 执行本地事物的地方,一般是执行数据插入
		int value = transactionIndex.getAndIncrement();
		int status = value % 3;
		localTrans.put(msg.getTransactionId(), status);
		// 执行数据库插入,如果成功就返回commit
		return LocalTransactionState.UNKNOW;
	}

	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt msg) {

		log.info("transcation msg check,thread name{}, {}", Thread.currentThread().getName(), msg);

		// 根据MessageExt 回查信息
		Integer status = localTrans.get(msg.getTransactionId());
		if (status != null) {
			switch (status) {
			case 0:
				return LocalTransactionState.UNKNOW;
			case 1:
				return LocalTransactionState.COMMIT_MESSAGE;
			case 2:
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}

		}
		return LocalTransactionState.COMMIT_MESSAGE;
	}

}
