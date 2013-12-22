package com.tcc.cti.driver.common;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.common.PasswordUtils;
/**
 * 单元测试{@link PasswordUtils}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PasswordUtilsTest {
	
	@Test
	public void testEncodeMD5IsNull(){
		String p = PasswordUtils.encodeMD5(null);
		Assert.assertNull(p);
	}
	
	@Test
	public void testEncodeMD5(){
		String password = "1";
		
		String p = PasswordUtils.encodeMD5(password);
		Assert.assertEquals("c4ca4238a0b923820dcc509a6f75849b", p);
	}
}
