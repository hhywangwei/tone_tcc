package com.tcc.cti.web.sys.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.web.common.util.Const;
import com.tcc.cti.web.server.CtiServerFacable;
import com.tcc.cti.web.sys.model.SysUser;
import com.tcc.cti.web.sys.service.SysUserService;
/**
 * 用户登录显示控制
 * 
 * @author hantongshan
 */
@Controller
@RequestMapping(value = "/sys/login")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static final String SYS_LOGIN_PATH = "../../login";//登录页面
	private static final String SYS_MAIN_PATH = "../../main_zx";//系统管理主页面
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CtiServerFacable ctiServerFacable;
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return SYS_LOGIN_PATH;
	}
	
	/**
	 * 请求登录，验证用户
	 * @param loginname
	 * @param password
	 * @return
	 * @author hantongshan
	 * @modify time 2013-8-17 11:00:45 PM
	 */
	@RequestMapping(value="/loginCheck",method=RequestMethod.POST)
	public ModelAndView loginCheck(HttpSession session,@RequestParam String uname,@RequestParam String upass){
		logger.info("into loginCheck,uname:"+uname+",upass:"+upass);
		ModelAndView mv = new ModelAndView();
		String errInfo = "";
		SysUser user = sysUserService.findSysUserByUnamePwd(uname, upass);
		if (user != null) {
			if(user.getUserLimit()==1){//1企业管理员-->登录
			}else if(user.getUserLimit()==2){//2 企业坐席-->登录
			}else if(user.getUserLimit()==3){//3企业班长-->登录
			}
			logger.info("login succ,uname:"+uname+",upass:"+upass);
			session.setAttribute(Const.SESSION_USER, user);
		} else {
			logger.info("login fail,uname:"+uname+",upass:"+upass);
			errInfo = "用户名或密码有误！";
		}
		if (StringUtils.isEmpty(errInfo)) {
			mv.setViewName("redirect:../../main_zx.jsp");
		} else {
			mv.addObject("errInfo", errInfo);
			mv.addObject("uname", uname);
			mv.addObject("upass", upass);
			mv.setViewName(SYS_LOGIN_PATH);
		}
		return mv;
	}
	
	/**
	 * 访问系统首页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys_main")
	public String sys_main(Model model){
		return "redirect:../../main_zx.jsp";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/default")
	public String defaultPage(){
		return "default";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
//		session.removeAttribute(Const.SESSION_USER);
//		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
//		session.removeAttribute(Const.SESSION_USER_RIGHTS);
		return SYS_LOGIN_PATH;
	}
	
	/*
	 * 登录CTI引擎,需要传入相关参数
	 * @param HttpSession
	 * @param seatnumber 坐席号码
	 * @param
	 * 
	 * @return
	 * */
	@RequestMapping(value="/loginCTI")
	public ModelAndView loginCTI(HttpSession session,@RequestParam String seatnumber){
		ModelAndView mv = new ModelAndView();
		try {
			SysUser user = (SysUser) session.getAttribute("sessionUser");
			Operator operator = new Operator(String.valueOf(user.getCompanyid()), user.getUname());
			Response response = ctiServerFacable.login(operator, user.getUpass(), seatnumber, user.getUserLimit().toString());
			logger.info("返回值为{}",response.toString());
		} catch (Exception e) {
			logger.error("login CTI error", e);
		}
		mv.setViewName("redirect:../../main_zx.jsp");
		return mv;
	}

}

