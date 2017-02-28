package com.xu.RedisTest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xu.redis.client.RedisClient;
import com.xu.redis.model.Product;
import com.xu.redis.model.User;

import redis.clients.jedis.Tuple;

public class Test {
     
	public static void main(String[] args) throws InterruptedException {
	/*	User user1 = new User();
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
		}*/
		
		/*RedisClient.zAddSortSet("fruit", 10.0, "apple1");
		RedisClient.zAddSortSet("fruit", 11.0, "apple2");
		RedisClient.zAddSortSet("fruit", 12.0, "apple3");
		RedisClient.zAddSortSet("fruit", 13.0, "apple4");
		RedisClient.zAddSortSet("fruit", 3.0, "apple5");
		RedisClient.zAddSortSet("fruit", 15.0, "apple6");
		RedisClient.zAddSortSet("fruit", 16.0, "apple7");
		
		Set<Tuple> set = RedisClient.zRvRangeWithScoreSortSet("fruit", 0l, -1l);
		for(Tuple t:set){
			System.out.println("元素:--->"+t.getElement()+"权重----->"+t.getScore());
		}*/
		Product product = new Product();
		product.setPrice(23.2);
		product.setTitle("哈密瓜001");
		addGoods(product);
		for(int i = 0;i<=300;i++){
			product.setTitle("产品title:"+System.currentTimeMillis());
			product.setPrice(2000.4);
			addGoods(product);
		}
		
		
	}
	public static void addGoods(Product product){
		Long id = System.currentTimeMillis();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		map.put("title", product.getTitle());
		map.put("price", String.valueOf(product.getPrice()));
		RedisClient.sAdd("product:"+id, map);
	}

}
