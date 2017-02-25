package com.xu.RedisTest;
import java.util.Date;
import java.util.List;

import com.xu.redis.client.RedisClient;
import com.xu.redis.model.User;

public class Test {
     
	public static void main(String[] args) throws InterruptedException {
		User user1 = new User();
		user1.setEmail("sdjwejkwefjewbf");
		user1.setLoginName("loginName");
		user1.setMakeTime(new Date());
		user1.setName("徐国强");
		RedisClient.setObject("time", user1, 600);
		Thread.sleep(2000l);
		System.out.println(RedisClient.getRemainTimeByKey("time"));
		
		for(int i = 0;i<=100;i++){
			User user2 = new User();
			user2.setEmail("sdjwejkwefjewbf");
			user2.setLoginName("loginName111"+i);
			user2.setMakeTime(new Date());
			user2.setName("徐国强");
			Long address = RedisClient.setIntoListByLpush("test1", user2);
			System.out.println("push的位置为:"+address);
		}
		
		List<User>  list  = RedisClient.getRangeListByKey("test1", 0l, -1l, User.class);
		for(User user:list){
			System.out.println(user.getLoginName());
		}
	}

}
