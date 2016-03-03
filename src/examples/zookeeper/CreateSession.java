package examples.zookeeper;
import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;


public class CreateSession implements Watcher { 

	private static ZooKeeper zookeeper;
	public static void main(String[] args) throws IOException, InterruptedException {
//		实例化zookeeper
		zookeeper = new ZooKeeper("localhost:2181",5000,new CreateSession());
//		获取zookeeper的状态 异步的
		System.out.println(zookeeper.getState());
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(){
		
		System.out.println("do something");
	}
	@Override
	public void process(WatchedEvent event) {
//		事件处理函数
		// TODO Auto-generated method stub
//		输出watcher接受到的时间
		System.out.println("收到事件："+event);
		if (event.getState()==KeeperState.SyncConnected){
//			从事件中得到当前服务器端的状态  判断是不是等于系统预设的状态
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething();
			}
		}
	}
	
}
