package com.red.verb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * application
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 14:57
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}
}
