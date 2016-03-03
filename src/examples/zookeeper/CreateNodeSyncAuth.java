package examples.zookeeper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;


public class CreateNodeSyncAuth implements Watcher { 

	private static ZooKeeper zookeeper;
//	解决局域网断线问题
//	声明一个布尔变量，赋值为假

	private static boolean somethingDone = false;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		zookeeper = new ZooKeeper("localhost:2181",5000,new CreateNodeSyncAuth());
		System.out.println(zookeeper.getState());
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	/*
	 * 权限模式(scheme): ip, digest  基于ip和基于用户名密码
	 * 授权对象(ID)
	 * 		ip权限模式:  具体的ip地址
	 * 		digest权限模式: username:Base64(SHA-1(username:password))
	 * 						用户名：密码加密后进行Base64编码
	 * 权限(permission): create(C), DELETE(D),READ(R), WRITE(W), ADMIN(A) 
	 * 		注：单个权限，完全权限，复合权限
	 * 
	 * 权限组合: scheme + ID + permission
	 * 
	 * 
	 * 
	 * */
	
	private void doSomething(){
		try {
//			表示允许从ip地址为172.29.10.4发起的客户端读取zookeeper中某一个或者多个节点的信息
			ACL aclIp = new ACL(Perms.READ,new Id("ip","172.29.10.4"));
//			基于用户名和密码 DigestAuthenticationProvider zookeeper提供的加密类
			ACL aclDigest = new ACL(Perms.READ|Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("user1:123456")));
			ArrayList<ACL> acls = new ArrayList<ACL>();
			acls.add(aclDigest);
			acls.add(aclIp);
			//zookeeper.addAuthInfo("digest", "jike:123456".getBytes());			
			String path = zookeeper.create("/node_2", "123".getBytes(), acls, CreateMode.PERSISTENT);
//			zookeeper预设的授权方式 OPEN_ACL_UNSAFE任何人可以做任何操作，READ_ACL_UNSAFE任何人具读取权限，
//			CREATOR_ALL_ACL表示系统将使用addAuthInfo注册信息来作为新创建节点的授权信息
//			String path = zookeeper.create("/node_2", "123".getBytes(), Ids.CREATOR_ALL_ACL ,CreateMode.PERSISTENT);
			System.out.println("return path:"+path);
//			当所有代码执行完后将布尔变量赋值为true
			somethingDone = true;
			
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("收到事件："+event);
		if (event.getState()==KeeperState.SyncConnected){
//			在事件处理函数的条件判中加入SomethingDone
			if (!somethingDone && event.getType()==EventType.None && null==event.getPath()){
				doSomething();
			}
		}
	}
	
}
