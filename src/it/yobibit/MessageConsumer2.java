package it.yobibit;

import com.lmax.disruptor.EventHandler;

public class MessageConsumer2 implements EventHandler<Message> {

	@Override
	public void onEvent(Message message, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("Consumer2 | Message: " + message.content);
		message.content = message.content.toLowerCase();
		
		if (endOfBatch) {
			System.out.println("Consumer2 | End Of Batch!");
		}		
	}
}