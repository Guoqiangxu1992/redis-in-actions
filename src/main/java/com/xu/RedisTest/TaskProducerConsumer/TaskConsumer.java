package com.xu.RedisTest.TaskProducerConsumer;

import java.util.Random;

import com.xu.redis.client.RedisClient;

public class TaskConsumer implements Runnable{

	public void run() {
		Random random = new Random(); 
		while(true){
			String taskId = RedisClient.rpoplpush("taskIdOneQuene", "tempTaskQuene");
			
			//模拟业务逻辑
			try {
				Thread.sleep(3000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			if(random.nextInt(13)%7==0){
				System.out.println("处理任务失败，任务id为:"+taskId+"------>处理失败的任务重新放回队列里面处理");
				//处理失败的任务重新放回队列里面处理
				RedisClient.rpoplpush("tempTaskQuene", "taskIdOneQuene");
			}else{
				RedisClient.rpop(taskId);
				System.out.println("taskIdOneQuene任务队列任务id:"+taskId+"------->处理成功");
			}
		}
	}

}
