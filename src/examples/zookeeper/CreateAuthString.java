package examples.zookeeper;

import java.security.NoSuchAlgorithmException;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;


public class CreateAuthString {

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		zookeeper的密文生成函数
		System.out.println(DigestAuthenticationProvider.generateDigest("user1:123456"));
	}
}
