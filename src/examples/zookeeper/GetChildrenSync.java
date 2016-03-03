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

//��ȡ�ӽڵ�
public class GetChildrenSync implements Watcher{
	
	
    private static ZooKeeper zooKeeper;
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
				
		zooKeeper = new ZooKeeper("localhost:2181",5000,new GetChildrenSync());
		System.out.println(zooKeeper.getState().toString());
			
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(ZooKeeper zooKeeper){
		
		try {
//			getchildren(��ȡÿһ���ڵ���ӽڵ���Ϣ����ȡ�ڵ��б��ͬʱҪ��Ҫ��ע����ڵ��ӽڵ�ı仯����עtrue,����עfalse)
//			�����ӽڵ���б�
			List<String> children =  zooKeeper.getChildren("/", true);
			System.out.println(children);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
//		zookeeper�¼�������
//		��ǰzookeeper��״̬
		if (event.getState()==KeeperState.SyncConnected){
//			������ǰ�¼��Ľڵ�
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}else{
//				��ǰ�¼����¼�����
				if (event.getType()==EventType.NodeChildrenChanged){
					try {
//						�õ������仯���ӽڵ�
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
