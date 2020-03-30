package com.example.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.alibaba.fastjson.JSON;

@Service
public class RedisUtil {
  public static final Integer TTL = 10;
  public static final Long TIMEOUT = 30L;
  public static final String KEY_ENVIRONMENT_PREFIX = "ENVIRONMENT_PREFIX";

  @Resource(name = "writeRedisTemplate")
  private RedisTemplate<String, Object> writeRedisTemplate;

  @Resource(name = "readRedisTemplate")
  private RedisTemplate<String, Object> readRedisTemplate;

  private final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

  public void putString(String key, String value, Integer timeoutSeconds) {
    try {
      Assert.notNull(key, "redis key must be not null.");
      Assert.notNull(value, "value  must be not null.");
      writeRedisTemplate.opsForValue().set(key, value);

      if (timeoutSeconds > 0) {
        writeRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      logger.error("putString in redis fail, reason: ", e);
    }
  }

  public void putString(String key, String value) {
    putString(key, value, null);
  }

  public String getString(String key) {
    try {
      return (String) readRedisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      logger.error("getString in redis fail, reason: ", e);
    }
    return null;
  }

  // Map
  public void addOrUpdateAll(String key, Map<String, String> valueMap, Integer timeoutSeconds) {
    try {
      Assert.notNull(key, "redis key must be not null.");
      Assert.notEmpty(valueMap, "valueMap  must be not null.");
      writeRedisTemplate.delete(key);
      writeRedisTemplate.opsForHash().putAll(key, valueMap);
      if (timeoutSeconds != null && timeoutSeconds > 0) {
        writeRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      logger.error("addOrUpdateAll in redis fail, reason: ", e);
    }
  }

  public void addOrUpdateAll(String key, Map<String, String> valueMap) {
    addOrUpdateAll(key, valueMap, null);
  }

  public void addOrUpdateSome(String key, Map<String, String> valueMap, Integer timeoutSeconds) {
    try {
      Assert.notNull(key, "redis key must be not null.");
      Assert.notEmpty(valueMap, "valueMap  must be not null.");
      writeRedisTemplate.opsForHash().putAll(key, valueMap);
      if (timeoutSeconds != null && timeoutSeconds > 0) {
        writeRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      logger.error("addOrUpdateSome in redis fail, reason: ", e);
    }
  }

  public void addOrUpdateSome(String key, Map<String, String> valueMap) {
    addOrUpdateSome(key, valueMap, null);
  }

  public Map<String, String> getHashValue(String key) {
    try {
      Assert.notNull(key, "redis key must be not null.");
      Map<Object, Object> valueMap = readRedisTemplate.opsForHash().entries(key);
      if (valueMap == null || valueMap.isEmpty()) {
        return null;
      }
      Map<String, String> result = new HashMap<String, String>();
      Set<Object> hashKeys = valueMap.keySet();
      for (Object hashKey : hashKeys) {
        if (hashKey != null && valueMap.get(hashKey) != null) {
          result.put(hashKey.toString(), valueMap.get(hashKey).toString());
        }
      }
      return result;
    } catch (Exception e) {
      logger.error("getHashValue in redis fail, reason: ", e);
    }
    return null;
  }

  public String getHashValue(String key, String hashKey) {
    try {
      Assert.notNull(key, "redis key must be not null.");
      Assert.notNull(hashKey, "redis hashKey must be not null.");
      Object result = readRedisTemplate.opsForHash().get(key, hashKey);
      if (result != null) {
        return result.toString();
      }
    } catch (Exception e) {
      logger.error("getHashValue in redis fail, reason: ", e);
    }
    return null;
  }

  public void deleteHashValue(String key, String hashKey) {
    try {
      Assert.notNull(key, "redis key must be not null.");
      if (hashKey == null) {
        writeRedisTemplate.delete(key);
      } else {
        writeRedisTemplate.opsForHash().delete(key, hashKey);
      }
    } catch (Exception e) {
      logger.error("deleteHashValue in redis fail, reason: ", e);
    }
  }

  Set<String> keys(String pattern){
    return readRedisTemplate.keys(pattern);
  }
  
  public boolean hasKey(String key) {
    return readRedisTemplate.hasKey(key);
  }
  
  public Long delete(Set<String> keys) {
    return writeRedisTemplate.delete(keys);
  }
  
  public Long delete(Collection<String> keys) {
    return writeRedisTemplate.delete(keys);
  }  
  
  public Boolean delete(String key) {
    return writeRedisTemplate.delete(key);
  }
  
  // List
  public void addObjectToList(String key, Object obj, boolean isRightPush, Integer timeoutSeconds) {
    Assert.notNull(key, "redis key must be not null.");
    String jsonData = JSON.toJSONString(obj);
    if (isRightPush) {
      writeRedisTemplate.opsForList().rightPush(key, jsonData);
    } else {
      writeRedisTemplate.opsForList().leftPush(key, jsonData);
    }
    if (timeoutSeconds != null && timeoutSeconds > 0) {
      writeRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
    }
  }

  public void addObjectToList(String key, Object obj) {
    addObjectToList(key, obj, false, null);
  }

  public void deleteListAll(String key) {
    writeRedisTemplate.delete(key);
  }
  
  public void addOrUpdateListAll(String key, List<Object> objs, Integer timeoutSeconds) {
    Assert.notNull(key, "redis key must be not null.");
    writeRedisTemplate.delete(key);
    List<String> datas = new ArrayList<String>(objs.size());
    for (Object obj : objs) {
      String jsonData = JSON.toJSONString(obj);
      datas.add(jsonData);
    }
    writeRedisTemplate.opsForList().leftPushAll(key, datas);
    if (timeoutSeconds != null && timeoutSeconds > 0) {
      writeRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
    }
  }

  public void addOrUpdateListAll(String key, List<Object> objs) {
    addOrUpdateListAll(key, objs, null);
  }

  public void addOrUpdateListSome(String key, List<Object> objs, boolean isRightPush, Integer timeoutSeconds) {
    Assert.notNull(key, "redis key must be not null.");
    List<String> datas = new ArrayList<String>(objs.size());
    for (Object obj : objs) {
      String jsonData = JSON.toJSONString(obj);
      datas.add(jsonData);
    }
    if (isRightPush) {
      writeRedisTemplate.opsForList().rightPushAll(key, datas);
    } else {
      writeRedisTemplate.opsForList().leftPushAll(key, datas);
    }
    if (timeoutSeconds != null && timeoutSeconds > 0) {
      writeRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
    }
  }

  public void addOrUpdateListSome(String key, List<Object> objs) {
    addOrUpdateListSome(key, objs, false, null);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public List<String> listDatas(String key, Integer start, Integer end) {
    Assert.notNull(key, "redis key must be not null.");
    if (start == null) {
      start = 0;
    }
    if (end == null) {
      end = -1;
    }

    List<Object> datas = readRedisTemplate.opsForList().range(key, start, end);
    return (List<String>) (List) datas;
  }

  public synchronized void lock(String key, Integer timeOutSecond) {
    String randomStr = UUID.randomUUID().toString();
    try {
      Boolean result = writeRedisTemplate.opsForValue().setIfAbsent(key, randomStr, timeOutSecond, TimeUnit.SECONDS);
      while (result == null || (!result)) {
        Thread.sleep(TIMEOUT);
        result = writeRedisTemplate.opsForValue().setIfAbsent(key, randomStr, timeOutSecond, TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      logger.error("set lock fail: ", e);
    }
  }

  public synchronized void lock(String key) {
    lock(key, TTL);
  }

  public boolean tryLock(String key, Integer timeOutSecond) {
    String randomStr = UUID.randomUUID().toString();
    Boolean result = writeRedisTemplate.opsForValue().setIfAbsent(key, randomStr, timeOutSecond, TimeUnit.SECONDS);
    return result != null && result;
  }

  public void unlock(String key) {
    writeRedisTemplate.delete(key);
  }

  public synchronized boolean isLock(String key) {
    return writeRedisTemplate.hasKey(key);
  }

  // increment
  public RedisAtomicInteger setAtomicInteger(String key, Integer initValue, Integer timeoutHour) {
    try {
      RedisAtomicInteger redisAtomicInteger =
          new RedisAtomicInteger(key, writeRedisTemplate.getConnectionFactory(), initValue);
      if (timeoutHour != null && timeoutHour > 0) {
        writeRedisTemplate.expire(key, timeoutHour, TimeUnit.HOURS);
      }
      return redisAtomicInteger;
    } catch (Exception e) {
      logger.error("setAtomicInteger in redis fail, reason: ", e);
    }
    return null;
  }

  public Integer getAtomicInteger(String key) {
    try {
      if (writeRedisTemplate.hasKey(key)) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, writeRedisTemplate.getConnectionFactory());
        return redisAtomicInteger.get();
      }
    } catch (Exception e) {
      logger.error("getAtomicInteger in redis fail, reason: ", e);
    }
    return null;
  }

  public Integer getAndIncrementAtomicInteger(String key) {
    try {
      if (writeRedisTemplate.hasKey(key)) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, writeRedisTemplate.getConnectionFactory());
        return redisAtomicInteger.getAndIncrement();
      }
    } catch (Exception e) {
      logger.error("getAndIncrementAtomicInteger in redis fail, reason: ", e);
    }
    return null;
  }

  public void putKeyValue(String hashKey, String key, String value){
    writeRedisTemplate.opsForHash().put(hashKey, key, value);
  }
}

