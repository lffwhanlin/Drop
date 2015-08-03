package jafka.study;

import java.io.IOException;
import java.util.Properties;

import com.sohu.jafka.api.FetchRequest;
import com.sohu.jafka.consumer.SimpleConsumer;
import com.sohu.jafka.message.MessageAndOffset;
import com.sohu.jafka.producer.Producer;
import com.sohu.jafka.producer.ProducerConfig;
import com.sohu.jafka.producer.StringProducerData;
import com.sohu.jafka.producer.serializer.StringEncoder;
import com.sohu.jafka.utils.Utils;

public class JafkaClient {
	
	public static void main(String[] args) throws IOException{
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SimpleConsumer consumer = new SimpleConsumer("127.0.0.1", 9092);
			    //
			    long offset = 0;
			    while (true) {
			        FetchRequest request = new FetchRequest("demo", 0, offset);
			        try {
						for (MessageAndOffset msg : consumer.fetch(request)) {
						    System.out.println(Utils.toString(msg.message.payload(), "UTF-8"));
						    offset = msg.offset;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
				
			}
		}).start();
		

	    
	    
		
	}

}
