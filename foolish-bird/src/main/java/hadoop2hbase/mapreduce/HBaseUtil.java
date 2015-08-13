package hadoop2hbase.mapreduce;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;



public class HBaseUtil {
	
	private static Configuration configuration;
	
	static{
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.property.clientPort", "2181");  
        configuration.set("hbase.zookeeper.quorum", "121.201.13.251");  
        configuration.set("hbase.master", "121.201.13.251:600000");
        configuration.set("hbase.rootdir", "hdfs://121.201.13.251:9000/hbase");
	}


	/**
	 * 2015年8月13日
	 * 下午4:33:56
	 * @param puts
	 * @param tableName
	 * TODO 向HBase表中添加数据
	 */
	public static void insert(List<Put> puts,String tableName){
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);
			final TableName name = TableName.valueOf(tableName);
			Table table = connection.getTable(name);
			table.put(puts);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
