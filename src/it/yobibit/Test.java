package it.yobibit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;


public class Test {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws AlertException, InterruptedException, TimeoutException {
		// Executor that will be used to construct new threads for consumers
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
	        // The factory for the event
	        EventFactory<Message> factory = new MessageFactory();
	        
	        // Specify the size of the ring buffer, must be power of 2
	        int bufferSize = 1024;
	        
	        // Construct the Disruptor with a SingleProducerSequencer
	        Disruptor<Message> disruptor = new Disruptor<Message>(
	        		factory, 
	        		bufferSize, 
	        		executor,
	        		ProducerType.SINGLE, 
	        		new BlockingWaitStrategy());
	        try {
		        // Connect the handlers
		        disruptor
		        	.handleEventsWith(new MessageConsumer1())
		        	.then(new MessageConsumer2());
		        
		        // Start the Disruptor, starts all threads running
		        disruptor.start();
		        
		        // Get the ring buffer from the Disruptor to be used for publishing
		        RingBuffer<Message> ringBuffer = disruptor.getRingBuffer();
		        
		        MessageProducer producer = new MessageProducer(ringBuffer);
		        for (int i = 0; i < 1000; i++) {
		        	producer.onData("content_" + i);
		        }
	        } finally {
	        	disruptor.shutdown();
	        }
        } finally {
        	executor.shutdown();
        }
	}
}