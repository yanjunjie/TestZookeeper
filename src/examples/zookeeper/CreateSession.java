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
//		ʵ����zookeeper
		zookeeper = new ZooKeeper("localhost:2181",5000,new CreateSession());
//		��ȡzookeeper��״̬ �첽��
		System.out.println(zookeeper.getState());
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(){
		
		System.out.println("do something");
	}
	@Override
	public void process(WatchedEvent event) {
//		�¼�������
		// TODO Auto-generated method stub
//		���watcher���ܵ���ʱ��
		System.out.println("�յ��¼���"+event);
		if (event.getState()==KeeperState.SyncConnected){
//			���¼��еõ���ǰ�������˵�״̬  �ж��ǲ��ǵ���ϵͳԤ���״̬
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething();
			}
		}
	}
	
}
