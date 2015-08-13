package hadoop2hbase.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FoolishBirdReduce extends Reducer<Text, ObjectWritable, Text, ObjectWritable> {
	
	@Override
	protected void reduce(Text key, Iterable<ObjectWritable> value,
			Reducer<Text, ObjectWritable, Text, ObjectWritable>.Context arg2)
					throws IOException, InterruptedException {
		List<Put> puts = new ArrayList<Put>();
		Iterator<ObjectWritable> iterator = value.iterator();
		while(iterator.hasNext()){
			ObjectWritable objectWritable = iterator.next();
			Put put =  (Put)objectWritable.get();
			puts.add(put);
		}
		HBaseUtil.insert(puts, "hadoop2hbase");
	}
	
}
