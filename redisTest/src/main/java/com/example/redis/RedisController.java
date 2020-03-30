package com.example.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

@RestController()
@RequestMapping("/test")
public class RedisController {

  @Autowired
  private RedisUtil redisUtil;

  @PostMapping("/redis")
  public RedisResponse testPutKeys(@RequestBody RedisRequest redisRequest){
    String value = RandomStringUtils.randomAlphanumeric(10);
//    redisUtil.putString(redisRequest.getKey(), value, 100);
    System.out.println("put key:" + redisRequest.getKey() + ", value:" + value);
    RedisResponse redisResponse = new RedisResponse();
    redisResponse.setKey(redisRequest.getKey());
    redisResponse.setValue(value);
    return redisResponse;
  }

  @GetMapping("/redis/{key}")
  public String testPutKeys(@PathVariable String key){
    String value = redisUtil.getString(key);
    System.out.println("***get key:" + key + ", value:" + value);
    return value;
  }

  @DeleteMapping("/redis")
  public Boolean testDeleteKeys(@RequestBody RedisRequest redisRequest){
    System.out.println("***delete key:" + redisRequest.getKey());
    return redisUtil.delete(redisRequest.getKey());
  }

  /*
  @GetMapping("/getKeys/{key}")
  public String testGetKeys(@PathVariable String key){
    redisUtil.putString(key, "abc", 180);
    System.out.println("hasKey return: " + redisUtil.hasKey(key));
    System.out.println("keys return: " + redisUtil.keys("*os*"));
    redisUtil.delete(key);
    return "OK";
  }


  
  @GetMapping("/deleteKeys/{key}")
  public String testDeleteKeys(@PathVariable String key){
    redisUtil.putString(key, "abc", 180);
    Set<String> asSet = new HashSet<>();
    asSet.add(key);
    System.out.println("keys return: " + redisUtil.delete(asSet));
    return "OK";
  }
  
  @GetMapping("/addOrUpdateAll/{key}")
  public String testAddOrUpdateAll(@PathVariable String key){
    Map<String, String> aMap= new HashMap<>();
    aMap.put("1", "abc");
    aMap.put("2", "xyz");
    redisUtil.addOrUpdateAll(key, aMap, 60);
    
    Map<String, String> resultMap = redisUtil.getHashValue(key);
    System.out.println("value 1: " + resultMap.get("1"));
    System.out.println("value 2: " + resultMap.get("2"));
    
    redisUtil.deleteHashValue(key, "1");
    Map<String, String> resultMap2 = redisUtil.getHashValue(key);
    System.out.println("after delete, value 1: " + resultMap2.get("1"));
    System.out.println("after delete, value 2: " + resultMap2.get("2"));
    redisUtil.deleteHashValue(key, "2");
    
    return "OK";
  }
  
  @GetMapping("/addOrUpdateSome/{key}")
  public String testAddOrUpdateSome(@PathVariable String key){
    Map<String, String> aMap= new HashMap<>();
    aMap.put("1", "abc");
    aMap.put("2", "xyz");
    redisUtil.addOrUpdateAll(key, aMap, 60);
    
    Map<String, String> aMap2= new HashMap<>();
    aMap2.put("1", "opq");
    aMap2.put("3", "rst");
    redisUtil.addOrUpdateSome(key, aMap2, 60);
    
    Map<String, String> resultMap = redisUtil.getHashValue(key);
    System.out.println("value 1: " + resultMap.get("1"));
    System.out.println("value 2: " + resultMap.get("2"));
    System.out.println("value 3: " + resultMap.get("3"));
    
    redisUtil.deleteHashValue(key, "1");
    Map<String, String> resultMap2 = redisUtil.getHashValue(key);
    System.out.println("after delete, value 1: " + resultMap2.get("1"));
    System.out.println("after delete, value 2: " + resultMap2.get("2"));
    System.out.println("after delete, value 3: " + resultMap2.get("3"));
    redisUtil.deleteHashValue(key, "2");
    redisUtil.deleteHashValue(key, "3");
    
    return "OK";
  }
  
  
  @GetMapping("/addObjectToList/{key}")
  public String testAddObjectToListSome(@PathVariable String key){
    UserDto userDto = new UserDto();
    userDto.setAge(28);
    userDto.setName("cfd");
    userDto.setPhone("123");
    redisUtil.addObjectToList(key, userDto);
    redisUtil.addObjectToList(key, "abc");
    

    List<String> aList = redisUtil.listDatas(key, 0, null);
    System.out.println("value 0: " + aList.get(0));
    System.out.println("value 1: " + aList.get(1));
    
    redisUtil.deleteListAll(key);
    System.out.println("after delete, value: " + redisUtil.listDatas(key, 0, null));
    return "OK";
  }
  
  
  @GetMapping("/{key}")
  public List<UserDto> readTest(@PathVariable String key){
    String value = redisUtil.getHashValue(key, "data");

    List<UserDto> userDtos = JSON.parseArray(value, UserDto.class);
    System.out.print("value: " + userDtos.get(0).getName());
    return userDtos;
  }
  
  @GetMapping("/trylock/{key}")
  public boolean tryLockTest(@PathVariable String key){
    return redisUtil.tryLock(key, 60);
  }
  
  @GetMapping("/lock/{key}")
  public String lockTest(@PathVariable String key){
    redisUtil.lock(key, 60);
    System.out.println("lock: " + redisUtil.isLock(key));
    redisUtil.unlock(key);
    System.out.println("lock: " + redisUtil.isLock(key));
    return "OK";
  }
  
  @GetMapping("/setAtomicInteger/{key}")
  public String testSetAtomicInteger(@PathVariable String key){
    redisUtil.setAtomicInteger(key, 100, 60);
    
    System.out.println("AtomicInteger value: " + redisUtil.getAtomicInteger(key));
    
    System.out.println("AtomicInteger value2 : " + redisUtil.getAndIncrementAtomicInteger(key));
    return "OK";
  }
   */
}
