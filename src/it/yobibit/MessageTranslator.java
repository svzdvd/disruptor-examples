package it.yobibit;

import com.lmax.disruptor.EventTranslatorOneArg;

public class MessageTranslator implements EventTranslatorOneArg<Message, String> {

	@Override
	public void translateTo(Message message, long sequence, String content) {
		message.content = content;
	}
}