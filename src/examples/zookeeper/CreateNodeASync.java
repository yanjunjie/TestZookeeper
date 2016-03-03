package examples.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
/**
创建节点 ，异步创建
**/

public class CreateNodeASync implements Watcher { 

	private static ZooKeeper zookeeper;
	public static void main(String[] args) throws IOException, InterruptedException {
		zookeeper = new ZooKeeper("localhost:2181",5000,new CreateNodeASync());
		System.out.println(zookeeper.getState());
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(){
//	异步创建没有返回值
		zookeeper.create("/node_5", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,new IStringCallback(),"创建");
			
		
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
//	接口的实现类stringcallback 
	static class IStringCallback implements AsyncCallback.StringCallback{

		@Override
//		回调函数参数（返回码 成功返回0，路径，异步调用上下文，服务端返回来的已经创建节点的真实路径）
		public void processResult(int rc, String path, Object ctx, String name) {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder();
			sb.append("rc="+rc).append("\n");
			sb.append("path="+path).append("\n");
			sb.append("ctx="+ctx).append("\n");
			sb.append("name="+name);
			System.out.println(sb.toString());
			
		}
		
		
	}
	
}
