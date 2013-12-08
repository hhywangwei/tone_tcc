package com.tcc.cti.web.sys.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.cti.web.sys.dao.SysUserMapper;
import com.tcc.cti.web.sys.model.SysUser;
import com.tcc.cti.web.sys.service.SysUserService;
/**
 *系统管理员信息业务操作 
 * 
 * @author hantongshan
 * @author <a href="ls.hantongshan@gmail.com">hantongshan</a>
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	private Log log = LogFactory.getLog(SysUserServiceImpl.class);
	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 根据用户名,密码查询用户信息
	 * @param loginname	登录名
	 * @param password	密码
	 * @return SysUser
	 * 
	 * @author hantongshan
	 * @modify time 2013-8-17 11:14:45 pm
	 */
	public SysUser findSysUserByUnamePwd(String uname, String upass) {
		log.info("login getSysUserInfo uname:" + uname + ",upass:" + upass);
		SysUser user = new SysUser();
		user.setUname(uname);
		user.setUpass(upass);
		user = sysUserMapper.findSysUserByUnamePwd(user);
		return user;
	}
	
	
}
