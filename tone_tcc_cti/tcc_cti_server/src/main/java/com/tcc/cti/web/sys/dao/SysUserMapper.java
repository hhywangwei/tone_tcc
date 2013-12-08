package com.tcc.cti.web.sys.dao;

import org.springframework.stereotype.Repository;

import com.tcc.cti.web.sys.model.SysUser;
/**
 *WEB登入权限表信息操作接口 
 * 
 * @author hantongshan
 * @author <a href="ls.hantongshan@gmail.com">hantongshan</a>
 */
@Repository
public interface SysUserMapper {

	public SysUser findSysUserByUnamePwd(SysUser user);
}