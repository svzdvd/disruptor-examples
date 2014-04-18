package it.yobibit;

import com.lmax.disruptor.EventHandler;

public class MessageConsumer1 implements EventHandler<Message> {

	@Override
	public void onEvent(Message message, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("Consumer1 | Message: " + message.content);
		message.content = message.content.toUpperCase();
		
		if (endOfBatch) {
			System.out.println("Consumer1 | End Of Batch!");
		}
	}
}