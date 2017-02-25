package com.xu.RedisTest.MessagePubSubTask;

import com.xu.redis.client.RedisClient;

public class ReceiveMessage implements Runnable {

	public void run() {
		while (true) {
			ressiveMessage();
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void ressiveMessage() {
		RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
		RedisClient.receiveMessage(listener, "xuguoqiang");
	}

}
