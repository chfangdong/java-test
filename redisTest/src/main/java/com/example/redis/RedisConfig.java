package com.example.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.ObjectUtils;

@Configuration
public class RedisConfig {
  @Value("${spring.redis.host}")
  private String hostName;
  @Value("${spring.redis.port}")
  private int writePort;
  @Value("${spring.redis.read-port}")
  private int readPort;
  @Value("${spring.redis.password}")
  private String passWord;
  @Value("${spring.redis.pool.max-idle}")
  private int maxIdle;
  @Value("${spring.redis.pool.min-idle}")
  private int minIdle;
  @Value("${spring.redis.pool.max-wait}")
  private int maxWait;
  @Value("${spring.redis.database}")
  private int database;

  public RedisTemplate<String, Object> createRedisTemplate(int port) {
    // base config
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(hostName);
    redisStandaloneConfiguration.setPort(port);
    redisStandaloneConfiguration.setDatabase(database);
    if (!ObjectUtils.isEmpty(passWord)) {
      RedisPassword redisPassword = RedisPassword.of(passWord);
      redisStandaloneConfiguration.setPassword(redisPassword);
    }

    // connect pool
    GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
    genericObjectPoolConfig.setMinIdle(minIdle);
    genericObjectPoolConfig.setMaxIdle(maxIdle);
    genericObjectPoolConfig.setMaxWaitMillis(maxWait);

    // lettuce pool
    LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
    builder.poolConfig(genericObjectPoolConfig);
    LettuceConnectionFactory connectionFactory =
        new LettuceConnectionFactory(redisStandaloneConfiguration, builder.build());
    connectionFactory.afterPropertiesSet();

    // create redisTemplate
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.string());
    redisTemplate.setHashKeySerializer(RedisSerializer.string());
    redisTemplate.setHashValueSerializer(RedisSerializer.string());
    
    redisTemplate.setConnectionFactory(connectionFactory);
    return redisTemplate;
  }

  @Bean(name = "writeRedisTemplate")
  public RedisTemplate<String, Object> writeRedisTemplate() {
    return createRedisTemplate(writePort);
  }

  @Bean(name = "readRedisTemplate")
  public RedisTemplate<String, Object> readRedisTemplate() {
    return createRedisTemplate(readPort);
  }
}
