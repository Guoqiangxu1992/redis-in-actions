package com.xu.RedisTest.MessagePubSubTask;

public class GetMessageMain {

	public static void main(String[] args) {
		new Thread(new ReceiveMessage()).start();
		
		PublishMessage.start();
	}

}
