package com.xu.RedisTest.TaskProducerConsumer;

public class PSMain {

	public static void main(String[] args) throws InterruptedException {
		new Thread(new TaskProducer()).start();
		Thread.sleep(15000l);
		
		new Thread(new TaskConsumer()).start();
	}

}
