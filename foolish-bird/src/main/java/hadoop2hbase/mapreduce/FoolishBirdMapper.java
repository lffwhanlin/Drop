package hadoop2hbase.mapreduce;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FoolishBirdMapper extends Mapper<LongWritable, Text, Text, ObjectWritable> {
	
	private byte[] family = Bytes.toBytes("person");
	private byte[] family_name = Bytes.toBytes("name");
	private byte[] family_mark = Bytes.toBytes("mark");
	private byte[] family_age = Bytes.toBytes("age");
	private Text job_key = new Text(UUID.randomUUID().toString());

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ObjectWritable>.Context context)
			throws IOException, InterruptedException {
		if(value!=null){
			String[] columns = value.toString().split("\t");
			if(columns!=null&&columns.length==3){
				String name = columns[0];
				String mark = columns[1];
				String age = columns[2];
				Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
				put.addColumn(family, family_name, new Date().getTime(), Bytes.toBytes(name));
				put.addColumn(family, family_mark, new Date().getTime(), Bytes.toBytes(mark));
				put.addColumn(family, family_age, new Date().getTime(), Bytes.toBytes(age));
				context.write(job_key, new ObjectWritable(put));
			}
		}	
	}
	
	

}
