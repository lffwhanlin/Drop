package zookeeper.study;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class ZookeeperWatcher implements Watcher {
	
	private static ZookeeperWatcher instance = new ZookeeperWatcher();

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event);
	}
	
	public static ZookeeperWatcher getInstance(){
		return instance;
	}
	
}
