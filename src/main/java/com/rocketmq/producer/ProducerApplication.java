package com.rocketmq.producer;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zhangxianbin
 *
 *         2020年5月26日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ProducerApplication {

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().toUpperCase());
//		SpringApplication.run(ProducerApplication.class, args);
	}
}
