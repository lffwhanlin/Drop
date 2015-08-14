package hadoop2hbase.mapreduce;

import java.io.IOException;
import java.util.UUID;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FoolishBirdMapper extends Mapper<LongWritable, Text, Text, ObjectWritable> {
	
	private Text job_key = new Text(UUID.randomUUID().toString());

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ObjectWritable>.Context context)
			throws IOException, InterruptedException {
		if(value!=null){
			String[] columns = value.toString().split("\t");
			if(columns!=null&&columns.length==3){
				context.write(job_key, new ObjectWritable(columns));
			}
		}	
	}
	
	

}
