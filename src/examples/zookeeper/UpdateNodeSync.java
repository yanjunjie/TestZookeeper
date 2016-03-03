package examples.zookeeper;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Environment;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//修改节点数据 同步调用
public class UpdateNodeSync implements Watcher{
	
	
    private static ZooKeeper zooKeeper;
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zooKeeper = new ZooKeeper("localhost:2181",5000,new UpdateNodeSync());
		System.out.println(zooKeeper.getState().toString());
				
		Thread.sleep(Integer.MAX_VALUE);

	}
	
	private void doSomething(ZooKeeper zooKeeper){
		try {
//			setData(节点路径，节点新值，版本信息)
//			返回stat状态信息
			Stat stat = zooKeeper.setData("/node_1", "123".getBytes(), -1);
			System.out.println("stat:"+stat);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub

		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}
		
		}
	}

}
