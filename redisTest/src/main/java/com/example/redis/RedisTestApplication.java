package com.example.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("${CONFIG_FILE}")
public class RedisTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisTestApplication.class, args);

	}

}
