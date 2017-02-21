/**
 * 
 */
package com.xu.redis.client;

import java.util.Date;

import org.junit.Test;

import com.xu.redis.model.User;

/**
 * @author summer
 *
 */
public class TestClient {

	/**
	 * 测试字符串set 和get和delete
	 * */
	@Test
	public void testString() throws Exception {
		String value = "xu guoqiang what are you doing中文看下!";
		boolean flag = RedisClient.setString("xuguoqiang_key", value);
		if(flag==true){
			System.out.println("存入到redis成功,信息为value="+RedisClient.getString("xuguoqiang_key"));
			boolean deleteFlag = RedisClient.delString("xuguoqiang_key");
			if(deleteFlag){
				System.out.println("删除成功，value="+RedisClient.getString("xuguoqiang_key"));
			}
		}
	}
	
	
	/**
	 * 
	 * 测试对象正删改
	 * @throws InterruptedException 
	 * 
	 * */
	
	@Test
	public void testObject() throws InterruptedException{
		User user = new User();
		user.setEmail("sdjwejkwefjewbf");
		user.setLoginName("loginName");
		user.setMakeTime(new Date());
		user.setName("徐国强");
		boolean flag = RedisClient.setObject("userInfo", user);
		if(flag){
			User returnUser = RedisClient.getObject("userInfo", User.class);
			System.out.println("缓存成功，值为:"+returnUser.getName()+"名字"+returnUser.getEmail());
		}
		
		
		User user1 = new User();
		user1.setEmail("sdjwejkwefjewbf");
		user1.setLoginName("loginName");
		user1.setMakeTime(new Date());
		user1.setName("徐国强");
		boolean flag1 = RedisClient.setObject("userInfo1", user1,10);
		if(flag1){
			User user22 = RedisClient.getObject("userInfo1", User.class);
			System.out.println("缓存成功1111"+user22.getName());
			User user33 = RedisClient.getObject("userInfo1", User.class);
			Thread.sleep(2000l);
			System.out.println("缓存成功1111"+user33.getName());
			Thread.sleep(2000l);
			User user44 = RedisClient.getObject("userInfo1", User.class);
			System.out.println("缓存成功1111"+user44.getName());
			Thread.sleep(2000l);
			User user55 = RedisClient.getObject("userInfo1", User.class);
			System.out.println("缓存成功1111"+user55.getName());
			Thread.sleep(2000l);
			User user66 = RedisClient.getObject("userInfo1", User.class);
			System.out.println("缓存成功1111"+user66.getName());
			Thread.sleep(2000l);
			User user77 = RedisClient.getObject("userInfo1", User.class);
			System.out.println("缓存成功1111"+user77.getName());
			Thread.sleep(2000l);
			User user88 = RedisClient.getObject("userInfo1", User.class);
			System.out.println("缓存成功1111"+user88.getName());
			
		}
		
	}

}
