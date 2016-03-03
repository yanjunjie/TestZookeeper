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
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//修改节点数据 异步调用
public class UpdateNodeASync implements Watcher{
	
    private static ZooKeeper zooKeeper;
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zooKeeper = new ZooKeeper("localhost:2181",5000,new UpdateNodeASync());
		System.out.println(zooKeeper.getState().toString());
		
		
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(WatchedEvent event){
		//传入一个IStatCallback接口类的实例，异步调用上下文nul
		zooKeeper.setData("/node_6", "234".getBytes(), -1, new IStatCallback(),null);
	
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
//	实现了StatCallback接口
	static class IStatCallback implements AsyncCallback.StatCallback{

		@Override
//		返回码，节点路径，异步调用上下文，节点状态信息
		public void processResult(int rc, String path, Object ctx, Stat stat) {
			StringBuilder sb = new StringBuilder();
			sb.append("rc="+rc).append("\n");
			sb.append("path"+path).append("\n");
			sb.append("ctx="+ctx).append("\n");
			sb.append("Stat="+stat).append("\n");
			System.out.println(sb.toString());
			
		}		
		
	}

}
