package it.yobibit;

import com.lmax.disruptor.RingBuffer;

public class MessageProducer {

	private final RingBuffer<Message> ringBuffer;
	private static final MessageTranslator TRANSLATOR = new MessageTranslator();
		
    public MessageProducer(RingBuffer<Message> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

	public void onData(String content) {
        ringBuffer.publishEvent(TRANSLATOR, content);
    }
}