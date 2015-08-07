package zookeeper.study;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class CurrentNode {
	
	private static final int SESSIONS_TIME = 10000000;
	
	private static final String HOST = "121.201.13.251";

	public static void main(String[] args) {
		try {
			ZooKeeper zookeeper = new ZooKeeper(HOST,SESSIONS_TIME,ZookeeperWatcher.getInstance());
			for(int i=0;i<10000;i++){
				Thread.sleep(1000);
				List<String> list = zookeeper.getChildren("/root", true);
				if(list!=null&&!list.isEmpty()){
					for(String name : list){
						System.out.println(name);
					}
				}else{
					Stat stat = zookeeper.exists("/root/current1", true);
					if(stat==null){
						zookeeper.create("/root/current1", "HostB".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
