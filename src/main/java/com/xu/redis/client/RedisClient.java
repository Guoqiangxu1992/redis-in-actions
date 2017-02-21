package com.xu.redis.client;

import java.awt.geom.FlatteningPathIterator;
import java.util.ResourceBundle;

import org.springframework.aop.target.PoolingConfig;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * <p>
 * 	Redis客户端访问
 * </p>
 * 
 * @author xuguoqiang
 * @创建时间：2017年2月11日
 * @version： V1.0
 */
public class RedisClient {
	public  static  JedisPool jedisPool; // 池化管理jedis链接池
static {
		
		//读取相关的配置
		ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
		int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
		int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
		int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));
		
		String ip = resourceBundle.getString("redis.host");//本地redis服务器IP
		int port = Integer.parseInt(resourceBundle.getString("redis.port"));
		
		JedisPoolConfig config = new JedisPoolConfig();  
		//设置最大连接数
		config.setMaxTotal(maxActive);
		//设置最大空闲数
		config.setMaxIdle(maxIdle);
		//设置超时时间
		config.setMaxWaitMillis(maxWait);
		
		//初始化连接池
		jedisPool = new JedisPool(config, ip, port); 
	}


/**
 * 向缓存中设置字符串内容
 * @param key key
 * @param value value
 * @return
 * @throws Exception
 */
public static boolean  setString(String key,String value) throws Exception{
	Jedis jedis = null;
	try {
		jedis = jedisPool.getResource();
		jedis.set(key, value);
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}finally{
		jedisPool.returnResource(jedis);
	}
}

/**
 * 根据key 获取内容String字符串
 * @param key
 * @return
 */
public static Object getString(String key){
	Jedis jedis = null;
	try {
		jedis = jedisPool.getResource();
		Object value = jedis.get(key);
		return value;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}finally{
		jedisPool.returnResource(jedis);
	}
}

/**
 * 删除缓存中得对象，根据key
 * @param key
 * @return
 */
public static boolean delString(String key){
	Jedis jedis = null;
	try {
		jedis = jedisPool.getResource();
		jedis.del(key);
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}finally{
		jedisPool.returnResource(jedis);
	}
}


/**
 * 向缓存中设置对象
 * @param key 
 * @param value
 * @return
 */
public static boolean  setObject(String key,Object value){
	Jedis jedis = null;
	try {
		String objectJson = JSON.toJSONString(value);
		jedis = jedisPool.getResource();
		jedis.set(key, objectJson);
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}finally{
		jedisPool.returnResource(jedis);
	}
}

/**
 * 从缓存里面取对象
 * @param key 
 * @param value==object
 * @return
 * 
 * */

public static <T> T getObject(String key,Class <T> clazz){
	Jedis jedis = null;
	try{
		
		jedis = jedisPool.getResource();
		String value = jedis.get(key);
		return JSON.parseObject(value, clazz);
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}finally {
	   jedisPool.returnResource(jedis);
	}
}

/**
 * 设置对象缓存失效时间
 * expireTime 失效时间=seconds
 * */



public static boolean setObject(String key,Object object,int expireTime){
	Jedis jedis = null;
	try{
		String value = JSON.toJSONString(object);
		jedis = jedisPool.getResource();
		jedis.setex(key, expireTime, value);
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}
	return true;
}
     




}