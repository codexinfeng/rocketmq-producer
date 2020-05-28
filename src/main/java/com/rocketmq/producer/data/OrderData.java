package com.rocketmq.producer.data;

import java.util.ArrayList;
import java.util.List;

import com.rocketmq.producer.model.OrderInfo;


/**
 *
 *         2020年5月28日
 */
public class OrderData {

	public static List<OrderInfo> orderList = new ArrayList<>();
	static{
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111039L");
		orderInfo.setProductName("创建");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111065L");
		orderInfo.setProductName("创建");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111039L");
		orderInfo.setProductName("付款");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103117235L");
		orderInfo.setProductName("创建");
		orderList.add(orderInfo);
		
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111065L");
		orderInfo.setProductName("付款");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103117235L");
		orderInfo.setProductName("付款");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111065L");
		orderInfo.setProductName("完成");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111039L");
		orderInfo.setProductName("推送");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103117235L");
		orderInfo.setProductName("完成");
		orderList.add(orderInfo);
		
		orderInfo = new OrderInfo();
		orderInfo.setOrderId("15103111039L");
		orderInfo.setProductName("完成");
		orderList.add(orderInfo);
		
		
		
		
	}
}
