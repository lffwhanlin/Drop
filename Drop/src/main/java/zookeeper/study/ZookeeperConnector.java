package zookeeper.study;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class ZookeeperConnector {
	
	private static final int SESSIONS_TIME = 2000;
	
	private static final String HOST = "121.201.13.251";
	
	public static ZooKeeper getCon() throws IOException{
		
		ZooKeeper zookeeper = new ZooKeeper(HOST,SESSIONS_TIME,ZookeeperWatcher.getInstance());
		
		return zookeeper;
		
	}
	

}
