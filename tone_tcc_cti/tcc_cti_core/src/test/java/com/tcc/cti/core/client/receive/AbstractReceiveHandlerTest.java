package com.tcc.cti.core.client.receive;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.Requestsable;
import com.tcc.cti.core.message.response.Response;

/**
 * 单元测试 {@link AbstractReceiveHandler}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class AbstractReceiveHandlerTest {
	
	@Test
	public void testParseMessage()throws Exception{
		String message =  "<head>00161</head><msg>login</msg>"
				+ "<seq>1</seq><Type>1</Type><CompanyID>1</CompanyID>"
				+ "<OPID>8001</OPID><OPNumber>8002</OPNumber>"
				+ "<PassWord>c4ca4238a0b923820dcc509a6f75849b</PassWord>"; 
		
		ReceiveHandlerImpl handler = new ReceiveHandlerImpl();
		Map<String,String> map = handler.parseMessage(message);

		Assert.assertEquals(8, map.size());
		Assert.assertNull(map.get("root"));
		Assert.assertEquals("00161", map.get("head"));
		Assert.assertEquals("8002", map.get("OPNumber"));
	}

	@Test
	public void testReceiveHandler()throws Exception{
		OwnReceiveHandler handler = new OwnReceiveHandler();
		Map<String,String> content = new HashMap<String,String>();
		
		String companyId = "1";
		String opId = "1";
		Requestsable requests = Mockito.mock(Requestsable.class);
		Sessionable oc = Mockito.mock(Sessionable.class);
		Mockito.when(oc.getOperatorKey()).thenReturn(new OperatorKey(companyId,opId));
		handler.receiveHandler(requests, oc,"", content);
	}
	
	/**
	 * 实现{@link AbstractReceiverHandler}用于测试
	 * 
	 * @author <a href="hhywangwei@gmail.com">wangwei</a>
	 *
	 */
	private static class ReceiveHandlerImpl extends AbstractReceiveHandler{

		@Override
		protected boolean isReceive(String msgType) {
			// none instance
			return false;
		}

		@Override
		protected Response buildMessage(String companyId, String opId,
				String seq, Map<String, String> content) {
			// none instance
			return new Response("login",seq);
		}
		
	}
}
