package examples.zookeeper;



import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
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

//判断节点是否存在 同步调用

public class NodeExistsASync implements Watcher{
	
	
    private static ZooKeeper zooKeeper;
      
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		
		zooKeeper = new ZooKeeper("localhost:2181",5000,new NodeExistsASync());
		System.out.println(zooKeeper.getState().toString());		
		
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(ZooKeeper zookeeper){
		
//		节点路径，是否注册事件监听器，回调接口IStateCallback）
		zooKeeper.exists("/node_1", true, new IStateCallback(), null);
		
		

		
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub

		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}else{
				try {
					if (event.getType()==EventType.NodeCreated){
						System.out.println(event.getPath()+" created");
						zooKeeper.exists(event.getPath(), true, new IStateCallback(), null);
					}
					else if (event.getType()==EventType.NodeDataChanged){
						System.out.println(event.getPath()+" updated");
						zooKeeper.exists(event.getPath(), true, new IStateCallback(), null);
					}
					else if (event.getType()==EventType.NodeDeleted){
						System.out.println(event.getPath()+" deleted");
						zooKeeper.exists(event.getPath(), true, new IStateCallback(), null);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		
		}
	}
	
//    实现StatCallback接口
    static class  IStateCallback implements AsyncCallback.StatCallback{

		@Override
//		处理函数（返回码，节点路径，异步调用上下文，当前节点的状态信息
		public void processResult(int rc, String path, Object ctx, Stat stat) {
			System.out.println("rc:"+rc); 
			
		}
    	
    }

}
