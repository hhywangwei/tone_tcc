package com.tcc.cti.web.sys.service;

import com.tcc.cti.web.sys.model.SysUser;

/**
 *系统管理员信息业务接口 
 * 
 * @author hantongshan
 * @author <a href="ls.hantongshan@gmail.com">hantongshan</a>
 */
public interface SysUserService {
	
	public SysUser findSysUserByUnamePwd(String uname,String upass);

}
