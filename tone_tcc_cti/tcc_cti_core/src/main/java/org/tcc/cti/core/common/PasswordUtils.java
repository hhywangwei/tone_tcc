package org.tcc.cti.core.common;

/**
 * 密码处理工具类，密码在互联网上传统，使用明码传递有非常大安全隐患，所以密码在传递使用SHA加密方法。
 * <br><br>
 * 注意：这也不是安全方法，以后考虑使用SSL安全隧道方法处理
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PasswordUtils {

	public static String encodeMD5(String s){
		//TODO md5加密
		return null;
	}
	
	/**
	 * 用户密码加密为SHA的密码
	 * 
	 * @param s 密码
	 * @return
	 */
	public static String encodeSHA(String s){
		//TODO sha加密
		return null;
	}
	
	/**
	 * 对SHA加密密码解密
	 * 
	 * @param s SHA加密密码
	 * @return
	 */
	public static String decodeSHA(String s){
		//TODO sha解密
		return null;
	}
}
