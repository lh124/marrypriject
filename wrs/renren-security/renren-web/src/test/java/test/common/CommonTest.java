package test.common;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

public class CommonTest {

	@Test
	public void passwordTest(){
		String newPassword = new Sha256Hash("000000").toHex();
		System.out.println(newPassword);
	}
}
