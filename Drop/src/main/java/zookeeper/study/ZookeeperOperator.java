package zookeeper.study;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperOperator{

	public static void main(String[] args) {

        try {
        	
			ZooKeeper zookeeper = ZookeeperConnector.getCon();
			Stat stat = zookeeper.exists("/root/current", true);
			if(stat==null){
				zookeeper.create("/root/current", "HostA".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			}
			//Thread.sleep(30*2000);
			/*List<String> children = zookeeper.getChildren("/root", true);
			if(children!=null&&!children.isEmpty()){
				for(String child : children){
					System.out.println(child);
				}
			}
			//zookeeper.exists("/root", true);
			Stat stat = zookeeper.exists("/root/child",true);
			if(stat==null){
				//zookeeper.delete("/root/child", stat.getVersion());
				zookeeper.create("/root/child", "Root's child".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				//zookeeper.setData("/root/child", "Root's child".getBytes(),stat.getVersion());
			}
			//zookeeper.create("/root/child3", "Root's child".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);*/
			zookeeper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * 总结
	 * 1.通过exists方法中watcher参数设置为true，在观察者中可以接受到发生在该节点上的 NodeCreated，NodeDeleted，NodeDataChanged事件
	 * 2.通过getChildren方法中watcher参数设置为true，在观察者中可以接受到该节点上的NodeChildrenChanged事件
	 * 具体事件类型可以参见http://www.chepoo.com/zookeeper-watcher.html
	 * 
	 * 3.当zk客户端断开连接以后，该客户端建立的临时节点消失。
	 * 4.当zk服务器挂掉后，客户端收到异常ConnectionLoss，并且观察者会接受到state:Disconnected
	 * 关于zk会话超时可以参见：http://blog.csdn.net/kobejayandy/article/details/26289273
	 * 
	 */
	

}
