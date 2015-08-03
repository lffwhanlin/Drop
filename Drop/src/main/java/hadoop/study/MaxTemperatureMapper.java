package hadoop.study;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class MaxTemperatureMapper extends Mapper<LongWritable, Text,Text, IntWritable>{
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] columns = line.split("\t");
		if(columns!=null&&columns.length>=2){
			String year = columns[0];
			int temperature = Integer.parseInt(columns[1]);
			context.write(new Text(year), new IntWritable(temperature));
		}
	}
	
	

}
