package com.xu.RedisTest.TaskProducerConsumer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import com.xu.redis.client.RedisClient;

import redis.clients.jedis.Jedis;

/**
 * 模拟一个生产者生产消息
 * @author xuguoqiang
 * @version 1.0
 * @since
 * 
 * */

public class TaskProducer implements Runnable{
	
	Jedis jedis = new Jedis("192.168.5.130",6379);

	public void run() {
		Random random = new Random();
		while(true){
			try {
				
				//睡眠相当于处理业务逻辑
				Thread.sleep(random.nextInt(600)+600);
				String taskId = getFormat(new Date());
				RedisClient.IntoListByRpush("taskIdOneQuene",taskId);
				System.out.println("taskIdOneQuene队列插入一条任务------->"+taskId);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	public String getFormat(Date date){
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String StringDate = format2.format(date);
		return StringDate;
		
	}

}
