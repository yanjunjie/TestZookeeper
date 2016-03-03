package examples.zookeeper;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.sound.midi.VoiceStatus;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//节点删除 异步调用

public class DeleteNodeASync implements Watcher{
	
    private static ZooKeeper zooKeeper;
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zooKeeper = new ZooKeeper("localhost:2181",5000,new DeleteNodeASync());
		System.out.println(zooKeeper.getState().toString());
		
		
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(WatchedEvent event){
//		delete（节点路径，版本信息，回调接口，异步调用的上下文）
		zooKeeper.delete("/node_6", -1, new IVoidCallback(),null);
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(event);
			}
		}
	}
//	回调接口  需要实现VoidCallback接口
	static class IVoidCallback implements AsyncCallback.VoidCallback{

		@Override
//		processResult（返回码，节点路径，异步调用的上下文
		public void processResult(int rc, String path, Object ctx) {
			StringBuilder sb = new StringBuilder();
			sb.append("rc="+rc).append("\n");
			sb.append("path"+path).append("\n");
			sb.append("ctx="+ctx).append("\n");
			System.out.println(sb.toString());
			
		}		
		
	}

}
