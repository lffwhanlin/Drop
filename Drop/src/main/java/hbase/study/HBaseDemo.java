package hbase.study;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;


public class HBaseDemo {
	
	private static Configuration configuration;
	
	static{
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.property.clientPort", "2181");  
        configuration.set("hbase.zookeeper.quorum", "121.201.13.251");  
        configuration.set("hbase.master", "121.201.13.251:600000");
        configuration.set("hbase.rootdir", "hdfs://121.201.13.251:9000/hbase");
	}

	public static void main(String[] args) {
	
		try {
			HBaseDemo demo = new HBaseDemo();
			//demo.createTable("demo");
			//demo.insert();
			//demo.queryAll("demo");
			//demo.deleteFamily("demo", "column3");
			//demo.queryByRowkey("demo1", "demo");
			//demo.queryByCondition("demo");
			//demo.queryByMutilCondition("demo");
			//demo.deleteTable("demo");
			//demo.getTableList();
			demo.getNameSpace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:20:34
	 * @param tableName
	 * @throws Exception
	 * TODO HBase中创建表
	 */
	public void createTable(String tableName) throws Exception{
		Connection connection = ConnectionFactory.createConnection(configuration);
		Admin admin = connection.getAdmin(); 
		final TableName name = TableName.valueOf(tableName);
		if(admin.tableExists(name)){
			throw new Exception("The table is already exists");
		}else{
			HTableDescriptor descriptor = new HTableDescriptor(name);
			descriptor.addFamily(new HColumnDescriptor("column1"));
			descriptor.addFamily(new HColumnDescriptor("column2"));
			descriptor.addFamily(new HColumnDescriptor("column3"));
			admin.createTable(descriptor);
			admin.close();
		}
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:20:58
	 * @throws IOException
	 * TODO 向表中添加数据
	 */
	public void insert() throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		final TableName name = TableName.valueOf("demo");
		Table table = connection.getTable(name);
		Put put = new Put("demo2".getBytes());
		put.addColumn("column1".getBytes(), "name".getBytes(), new Date().getTime(), "hanlin".getBytes());
		put.addColumn("column2".getBytes(), "age".getBytes(), new Date().getTime(), "18".getBytes());
		//put.addColumn("column3".getBytes(), "address".getBytes(), new Date().getTime(), "beijing".getBytes());
		table.put(put);
		connection.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:21:15
	 * @param tableName
	 * @throws IOException
	 * TODO 查询表中的所有数据
	 */
	public void queryAll(String tableName) throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		final TableName name = TableName.valueOf(tableName);
		Table table = connection.getTable(name);
		ResultScanner rs = table.getScanner(new Scan());
		for(Result r : rs){
			String rowKey = new String(r.getRow());
			System.out.println(rowKey);
			for(Cell cell : r.rawCells()){
				System.out.println(new String(CellUtil.cloneFamily(cell))+"->"+new String(CellUtil.cloneQualifier(cell))+"->"+new String(CellUtil.cloneValue(cell)));
			}
		}
		connection.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:21:29
	 * @param tableName
	 * @param family
	 * @throws IOException
	 * TODO 删除表的列簇
	 */
	public void deleteFamily(String tableName,String family) throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		TableName name = TableName.valueOf(tableName);
		//Table table = connection.getTable(name);
		Admin admin = connection.getAdmin(); 
		admin.disableTable(name);
		admin.deleteColumn(name, family.getBytes());
		admin.enableTable(name);
		admin.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:21:45
	 * @param rowkey
	 * @param tableName
	 * @throws IOException
	 * TODO 根据主键查询数据
	 */
	public void queryByRowkey(String rowkey,String tableName) throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		TableName name = TableName.valueOf(tableName);
		Table table = connection.getTable(name);
		Get get = new Get(rowkey.getBytes());
		Result result = table.get(get);
		if(result!=null){
			for(Cell cell : result.rawCells()){
				System.out.println(new String(CellUtil.cloneFamily(cell))+"->"+new String(CellUtil.cloneQualifier(cell))+"->"+new String(CellUtil.cloneValue(cell)));
			}
		}
		connection.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:21:55
	 * @param tableName
	 * @throws IOException
	 * TODO 根据单一条件查询数据
	 */
	public void queryByCondition(String tableName) throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		TableName name = TableName.valueOf(tableName);
		Table table = connection.getTable(name);
		Filter filter = new SingleColumnValueFilter("column1".getBytes(), "name".getBytes(), CompareOp.EQUAL, "hanlin".getBytes());
		Scan scan = new Scan();
		scan.setFilter(filter);
		ResultScanner rs = table.getScanner(scan);
		for(Result r : rs){
			String rowKey = new String(r.getRow());
			System.out.println(rowKey);
			for(Cell cell : r.rawCells()){
				System.out.println(new String(CellUtil.cloneFamily(cell))+"->"+new String(CellUtil.cloneQualifier(cell))+"->"+new String(CellUtil.cloneValue(cell)));
			}
		}
		connection.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:22:07
	 * @param tableName
	 * @throws IOException
	 * TODO 根据多条件组合查询数据
	 */
	public void queryByMutilCondition(String tableName) throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		TableName name = TableName.valueOf(tableName);
		Table table = connection.getTable(name);
		List<Filter> filters = new ArrayList<Filter>();  
		Filter filter = new SingleColumnValueFilter("column1".getBytes(), "name".getBytes(), CompareOp.EQUAL, "hanlin".getBytes());
		Filter filter1 = new SingleColumnValueFilter("column2".getBytes(), "age".getBytes(), CompareOp.EQUAL, "33".getBytes());
		filters.add(filter);
		filters.add(filter1);
		FilterList list = new FilterList(filters);
		Scan scan = new Scan();
		scan.setFilter(list);
		ResultScanner rs = table.getScanner(scan);
		for(Result r : rs){
			String rowKey = new String(r.getRow());
			System.out.println(rowKey);
			for(Cell cell : r.rawCells()){
				System.out.println(new String(CellUtil.cloneFamily(cell))+"->"+new String(CellUtil.cloneQualifier(cell))+"->"+new String(CellUtil.cloneValue(cell)));
			}
		}
		connection.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:22:19
	 * @param tableName
	 * @throws IOException
	 * TODO 删除表
	 */
	public void deleteTable(String tableName) throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		TableName name = TableName.valueOf(tableName);
		//Table table = connection.getTable(name);
		Admin admin = connection.getAdmin(); 
		admin.disableTable(name);
		admin.deleteTable(name);
		admin.close();
	}
	
	/**
	 * 2015年8月11日
	 * 下午4:22:25
	 * @throws IOException
	 * TODO 获取表名
	 */
	public void getTableList() throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		Admin admin = connection.getAdmin();
		TableName[] tables = admin.listTableNames();
		if(tables!=null){
			for(TableName name : tables){
				System.out.println(name.getNameAsString());
			}
		}
		admin.close();
	}
	
	public void getNameSpace() throws IOException{
		Connection connection = ConnectionFactory.createConnection(configuration);
		Admin admin = connection.getAdmin();
		NamespaceDescriptor[] NamespaceDescriptors = admin.listNamespaceDescriptors();
		if(NamespaceDescriptors!=null){
			for(NamespaceDescriptor namespaceDescriptor : NamespaceDescriptors){
				System.out.println(namespaceDescriptor.getName());
			}
		}
	}
	
}
