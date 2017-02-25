package com.xu.RedisTest.MessagePubSubTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.xu.redis.client.RedisClient;

public class PublishMessage {

	public static void start() {
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String message = null;
			try {
				message = bufferReader.readLine();
				if (!"quit".equals(message)) {
					RedisClient.publishMessage("chanel_xuxuxu", message);
				} else {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
