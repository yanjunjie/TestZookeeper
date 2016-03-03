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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//获取子节点
public class GetChildrenSync implements Watcher{
	
	
    private static ZooKeeper zooKeeper;
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
				
		zooKeeper = new ZooKeeper("localhost:2181",5000,new GetChildrenSync());
		System.out.println(zooKeeper.getState().toString());
			
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(ZooKeeper zooKeeper){
		
		try {
//			getchildren(获取每一个节点的子节点信息，获取节点列表的同时要不要关注这个节点子节点的变化，关注true,不关注false)
//			返回子节点的列表
			List<String> children =  zooKeeper.getChildren("/", true);
			System.out.println(children);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
//		zookeeper事件监听器
//		当前zookeeper的状态
		if (event.getState()==KeeperState.SyncConnected){
//			触发当前事件的节点
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}else{
//				当前事件的事件类型
				if (event.getType()==EventType.NodeChildrenChanged){
					try {
//						得到发生变化的子节点
						System.out.println(zooKeeper.getChildren(event.getPath(), true));
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}		
		}
	}

}
