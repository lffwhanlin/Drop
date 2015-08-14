package hadoop2hbase.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FoolishBirdReduce extends Reducer<Text, ObjectWritable, Text, ObjectWritable> {
	
	private byte[] family = Bytes.toBytes("person");
	private byte[] family_name = Bytes.toBytes("name");
	private byte[] family_mark = Bytes.toBytes("mark");
	private byte[] family_age = Bytes.toBytes("age");
	
	@Override
	protected void reduce(Text key, Iterable<ObjectWritable> value,
			Reducer<Text, ObjectWritable, Text, ObjectWritable>.Context arg2)
					throws IOException, InterruptedException {
		List<Put> puts = new ArrayList<Put>();
		Iterator<ObjectWritable> iterator = value.iterator();
		while(iterator.hasNext()){
			ObjectWritable objectWritable = iterator.next();
			String[] columns = (String[])objectWritable.get();
			String name = columns[0];
			String mark = columns[1];
			String age = columns[2];
			Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
			put.addColumn(family, family_name, new Date().getTime(), Bytes.toBytes(name));
			put.addColumn(family, family_mark, new Date().getTime(), Bytes.toBytes(mark));
			put.addColumn(family, family_age, new Date().getTime(), Bytes.toBytes(age));
			puts.add(put);
		}
		HBaseUtil.insert(puts, "hadoop2hbase");
	}
	
}
