package com.tcc.cti.core.common;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码处理工具类,cti服务器使用md5加密。 <br>
 * <br>
 * 注意：这也不是安全方法，以后考虑使用SSL安全隧道方法处理
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PasswordUtils {
	private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

	/**
	 * MD5加密
	 * 
	 * @param source
	 *            密码
	 * @return
	 */
	public static String encodeMD5(String source) {
		String s = null;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = source.getBytes(Charset.forName("UTF-8"));
			md.update(bytes);
			byte[] tmp = md.digest();
			char[] str = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Encode md5 is \"{}\"", e);
		}
		return s;
	}
}
