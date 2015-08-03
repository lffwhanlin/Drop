package jafka.study;

import java.util.Properties;
import java.util.Random;

import com.sohu.jafka.producer.Producer;
import com.sohu.jafka.producer.ProducerConfig;
import com.sohu.jafka.producer.StringProducerData;
import com.sohu.jafka.producer.serializer.StringEncoder;

public class Productor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		add();
	}

	
	public static void add(){
		Properties props = new Properties();
	    props.put("broker.list", "0:127.0.0.1:9092");
	    props.put("serializer.class", StringEncoder.class.getName());
	    //
	    ProducerConfig config = new ProducerConfig(props);
	    Producer<String, String> producer = new Producer<String, String>(config);
	    //
	    StringProducerData data = new StringProducerData("demo");
	    data.add("message"+new Random().nextInt(10000000));
	    try {
	       producer.send(data);

	    }catch(Exception e){
	    	e.printStackTrace();
	    } finally {
	        producer.close();
	    }
	}
}
