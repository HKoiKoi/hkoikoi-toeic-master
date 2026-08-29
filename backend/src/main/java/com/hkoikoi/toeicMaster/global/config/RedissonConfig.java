package com.hkoikoi.toeicMaster.global.config;

import java.time.Duration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	private static final String REDIS_PREFIX = "redis://";

	@Value("${spring.data.redis.host:localhost}")
	private String host;

	@Value("${spring.data.redis.port:6379}")
	private int port;

	@Value("${spring.data.redis.password:}")
	private String password;

	@Value("${spring.data.redis.timeout:3000ms}")
	private Duration timeout;

	@Bean
	public RedissonClient redissonClient() {

		Config config = new Config();

		String redisAddress;
		if (password != null && !password.isBlank()) {
			redisAddress = String.format("%s:%s@%s:%d", REDIS_PREFIX, password, host, port);
		} else {
			redisAddress = String.format("%s%s:%d", REDIS_PREFIX, host, port);
		}

		config.useSingleServer()
			.setAddress(redisAddress)
			.setTimeout((int)timeout.toMillis());

		return Redisson.create(config);
	}
}
