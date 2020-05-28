package com.rocketmq.producer.model;

/**
 *
 *         2020年5月26日
 */
public class OrderInfo {

	private String productName;

	private String price;

	private String orderId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "orderId = " + orderId + ",productName = " + productName + ",price = " + price;
	}

}
