package com.xu.hessian;

import org.springframework.stereotype.Service;

import com.xu.redis.client.RedisClient;

@Service("helloService")
public class HelloServiceImpl implements HelloService{

	public void sayHello(String name) {
		System.out.println(name);
	}

	public Object set(String key, String value) {
		RedisClient.setObject(key, value);
		Object o = RedisClient.getString(key);
		System.out.println(RedisClient.getString(key).toString());
		return o;
	}

}
