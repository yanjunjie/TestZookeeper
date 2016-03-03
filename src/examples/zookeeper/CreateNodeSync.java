package examples.zookeeper;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
/**
创建节点 ，同步创建
**/
public class CreateNodeSync implements Watcher { 

	private static ZooKeeper zookeeper;
	public static void main(String[] args) throws IOException, InterruptedException {
		zookeeper = new ZooKeeper("localhost:2181",5000,new CreateNodeSync());
		System.out.println(zookeeper.getState());
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(){
		try {
//			创建节点create函数(路径，数据内容，为bayte数组。OPEN_ACL_UNSAFE任何人可使用，PERSISTENT持久节点
			String path = zookeeper.create("/node_4", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("return path:"+path);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("do something");
	}
	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("收到事件："+event);
		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething();
			}
		}
	}
	
}
