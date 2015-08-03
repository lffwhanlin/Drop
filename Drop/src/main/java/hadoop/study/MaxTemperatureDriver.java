package hadoop.study;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MaxTemperatureDriver extends Configured implements Tool{

	public static void main(String[] args) throws Exception{
		
		 int exitcode = ToolRunner.run(new MaxTemperatureDriver(), args);

         System.exit(exitcode);  
	}


	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2){
	
		    System.err.printf("Usage: %s <input><output>",getClass().getSimpleName());
		
		    ToolRunner.printGenericCommandUsage(System.err);
		
		    return -1;                  

		}                               
	
		Job job = Job.getInstance(getConf());
	
		job.setJobName("Max Temperature");                  
	
		job.setJarByClass(getClass());
	
		FileInputFormat.addInputPath(job,new Path(args[0]));
	
		FileOutputFormat.setOutputPath(job,new Path(args[1]));                  
	
		job.setMapperClass(MaxTemperatureMapper.class);
	
		job.setReducerClass(MaxTemperatureReducer.class);                  
	
		job.setOutputKeyClass(Text.class);
	
		job.setOutputValueClass(IntWritable.class);                  
	
		return job.waitForCompletion(true)?0:1;

	}

}
