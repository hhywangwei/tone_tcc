package com.tcc.cti.core.message.response;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.ResponseCode;

/**
 * 单元测试 {@link ResponseCode}

 * @author 《a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class ResponseCodeTest {

	@Test
	public void testGetDetail(){
		ResponseCode code = ResponseCode.InstanceCode;
		
		String detail = code.getDetail("0");
		Assert.assertEquals("正常返回", detail);
		detail = code.getDetail("9");
		Assert.assertEquals("工号不存在", detail);
		detail = code.getDetail("18");
		Assert.assertEquals("座席号码不匹配", detail);
	}
}
