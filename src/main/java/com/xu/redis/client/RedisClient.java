package com.xu.redis.client;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.xu.redis.model.User;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * 
 * <p>
 * Redis客户端访问
 * </p>
 * 
 * @author xuguoqiang @创建时间：2017年2月11日 @version： V1.0
 */
public class RedisClient {
	public static JedisPool jedisPool; // 池化管理jedis链接池

	static {

		// 读取相关的配置
		ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
		int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
		int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
		int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));

		String ip = resourceBundle.getString("redis.host");// 本地redis服务器IP
		int port = Integer.parseInt(resourceBundle.getString("redis.port"));

		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(maxActive);
		// 设置最大空闲数
		config.setMaxIdle(maxIdle);
		// 设置超时时间
		config.setMaxWaitMillis(maxWait);

		// 初始化连接池
		jedisPool = new JedisPool(config, ip, port);
	}

	/**
	 * 向缓存中设置字符串内容
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return
	 * @throws Exception
	 */
	public static boolean setString(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 根据key 获取内容String字符串
	 * 
	 * @param key
	 * @return
	 */
	public static Object getString(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Object value = jedis.get(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 删除缓存中得对象，根据key
	 * 
	 * @param key
	 * @return
	 */
	public static boolean delString(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 向缓存中设置对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setObject(String key, Object value) {
		Jedis jedis = null;
		try {
			String objectJson = JSON.toJSONString(value);
			jedis = jedisPool.getResource();
			jedis.set(key, objectJson);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 从缓存里面取对象
	 * 
	 * @param key
	 * @param value==object
	 * @return
	 * 
	 */

	public static <T> T getObject(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			String value = jedis.get(key);
			return JSON.parseObject(value, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 设置对象缓存失效时间 expireTime 失效时间=seconds
	 */

	public static boolean setObject(String key, Object object, int expireTime) {
		Jedis jedis = null;
		try {
			String value = JSON.toJSONString(object);
			jedis = jedisPool.getResource();
			jedis.setex(key, expireTime, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
		return true;
	}

	/**
	 * 
	 * 返回某个key剩余的时间，单位秒
	 * 
	 */

	public static Long getRemainTimeByKey(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.ttl(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/*********************************** Set的使用 *****************************************************/

	/**
	 * 
	 * 通过左边插入对象
	 * 
	 * @return 第几个元素
	 * 
	 */
	public static Long setIntoListByLpush(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String value1 = JSON.toJSONString(value);
			return jedis.lpush(key, value1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * 通过右边插入对象
	 * 
	 * @return 第几个元素
	 * 
	 */
	public static Long setIntoListByRpush(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String value1 = JSON.toJSONString(value);
			return jedis.rpush(key, value1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * 取出指定范围内的元素
	 * 
	 * @param <T>
	 * @param <T>
	 * @return List
	 * 
	 */

	@SuppressWarnings("unchecked")
	public static <T> List<T> getRangeListByKey(String key, Long i, Long j, Class<User> clazz) {
		Jedis jedis = null;
		List<T> list = new ArrayList<T>();
		try {
			jedis = jedisPool.getResource();
			List<String> resultList = jedis.lrange(key, i, j);
			for (String result : resultList) {
				list.add((T) JSON.parseObject(result, clazz));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/************************************* List ************************************************/

	/**
	 * 
	 * 通过左边边插入对象
	 * 
	 * @return 第几个元素
	 * 
	 */
	public static Long IntoListByRpush(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String value1 = JSON.toJSONString(value);
			return jedis.lpush(key, value1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * 从一个队列里面弹出一个插入到另外一个队列
	 * 
	 * @return 返回被插入临时队列的任务
	 * 
	 */
	public static String rpoplpush(String key1, String key2) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.rpoplpush(key1, key2);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * 从一个队列里面弹出一个并且清除
	 * 
	 * @return 返回被插入临时队列的任务
	 * 
	 */
	public static String rpop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * 订阅某一个频道的消息 第一个监听 第二个 频道
	 * 
	 * @return
	 * 
	 */
	public static boolean receiveMessage(JedisPubSub listener, String channels) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.subscribe(listener, channels);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * 发布给某一个频道的消息 第一个频道
	 * 
	 * @return 订阅者的数量
	 * 
	 */
	public static Long publishMessage(String channels, String message) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long receiver = jedis.publish(channels, message);
			return receiver;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

}
