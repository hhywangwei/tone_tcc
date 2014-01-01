package com.tcc.cti.web.server;

import java.util.List;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.response.Response;

/**
 * CTI服务操作接口，外部程序访问CTI服务器统一接口，对CTI driver驱动封装，可方便客户端调用。
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface CtiServerFacable {
	
	/**
	 * 登录CTI服务器
	 * 
	 * @param operator 操作员信息
	 * @param password 密码
	 * @param opNumber 登录电话
	 * @param type 座席类型
	 * @return 返回消息对象
	 * @throws CtiServerException
	 */
	Response login(Operator operator,String password,String opNumber,String type)throws CtiServerException;
	
	/**
	 * 退出CTI服务器
	 * 
	 * @param operator 操作员信息
	 * @return 返回消息对象
	 * @throws  CtiServerException
	 */
	Response logout(Operator operator)throws CtiServerException;
	
	/**
	 * 得到分组信息
	 * 
	 * @param operator 操作员对象
	 * @param groupId 查询指定的用户组编号，{@value null}时得到所有分组信息
	 * @return  返回{@link GroupResponse}分组对象列表
	 * @throws CtiServerException
	 */
	List<Response> getGroups(Operator operator,String groupId)throws CtiServerException;
	
	/**
	 * 得到分组成员信息
	 * 
	 * @param operator 操作员对象
	 * @param groupId 用户组编号
	 * @return 返回{@link GroupMemberReqsponse}用户组成员消息对象
	 * @throws CtiServerException
	 */
	List<Response> getGroupMembers(Operator operator,String groupId)throws CtiServerException;
	
	/**
	 * 得到本座席信息
	 * 
	 * @param operator 操作员对象
	 * @return 返回{@link OwnResponse}对象
	 * @throws CtiServerException
	 */
	Response getOwnInfo(Operator operator)throws CtiServerException;
	
	/**
	 * 得到座席信息
	 * 
	 * @param operator 操作员对象
	 * @param workId 工号,{@value null}时查询自己信息,<i>注意：工号不是"opID"，可以在{@link getGroupMembers}得到用户工号</i>
	 * @return 返回{@link OwnResponse}对象
	 * @throws CtiServerException
	 */
	Response getWorkInfo(Operator operator,String workId)throws CtiServerException;
	
	/**
	 * 得到班长信息列表
	 *  
	 * @param operator 操作员对象
	 * @return {@link MonitorResponse}对象
	 * @throws CtiServerException
	 */
	List<Response> getMonitors(Operator operator)throws CtiServerException;
	
	/**
	 * 得到电话呼叫信息
	 * 
	 * <pre>
	 * 这个方法非常重要，通过改方法客户可以得到电话状态，如：“震铃”、“接通”等。客户端可使用"Long poll"方式来调用。
	 * <i>注意：客户端是否存活也依赖该方法。</i>
	 * </pre>
	 * 
	 * @param operator
	 * @return
	 * @throws CtiServerException
	 */
	List<Response> getCall(Operator operator)throws CtiServerException;
	
	/**
	 * 座席外呼请求
	 * 
	 * @param operator 操作员编号
	 * @param opNumber 座席号码
	 * @param callNumber 呼叫号码
	 * @return {@link OutCallStateResponse}对象
	 * @throws CtiServerException
	 */
	Response outCall(Operator operator,String opNumber,String callNumber)throws CtiServerException;
	
	/**
	 * 取消外呼
	 * 
	 * @param operator 操作员对象
	 * @return 
	 * @throws CtiServerException
	 */
	Response outCallCancel(Operator operator)throws CtiServerException;
	
	/**
	 * 设置移动座席号码
	 * 
	 * @param operator 操作员对象
	 * @param number 移动座席号码
	 * @return
	 * @throws CtiServerException
	 */
	Response mobileNumber(Operator operator,String number)throws CtiServerException;
	
	/**
	 * 移动座席号码取消
	 * 
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response mobileNumberCancel(Operator operator)throws CtiServerException;
	
	/**
	 * 改变用户密码
	 * 
	 * @param operator 操作员对象
	 * @param password 密码
	 * @return
	 * @throws CtiServerException
	 */
	Response changePassword(Operator operator,String password)throws CtiServerException;
	
	/**
	 * 请求休息
	 *  
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response resum(Operator operator)throws CtiServerException;
	
	/**
	 * 恢复工作
	 * 
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response rest(Operator operator)throws CtiServerException;
	
	/**
	 * 静音
	 * 
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response silence(Operator operator)throws CtiServerException;
	
	/**
	 * 关闭静音
	 * 
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response closeSilence(Operator operator)throws CtiServerException;
	
	/**
	 * 保存通话
	 * 
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response callHold(Operator operator)throws CtiServerException;
	
	/**
	 * 关闭保存通话
	 * 
	 * @param operator 操作员对象
	 * @return
	 * @throws CtiServerException
	 */
	Response closeCallHold(Operator operator)throws CtiServerException;
	
	/**
	 * 通话帮助
	 * 
	 * @param operator 操作员对象
	 * @param workId 帮助工号
	 * @param number 帮助号码
	 * @param flag 0－求助，1－三方会议
	 * @return
	 * @throws CtiServerException
	 */
	Response callHelp(Operator operator,String workId,String number,String flag)throws CtiServerException;
	
	/**
	 * 转接到组
	 * 
	 * @param operator 操作员对象
	 * @param groupId 组编号
	 * @return
	 * @throws CtiServerException
	 */
	Response transferGroup(Operator operator,String groupId)throws CtiServerException;
	
	/**
	 * 转接到某一用户
	 * 
	 * @param operator 操作员对象
	 * @param workId 工作编号
	 * @param number 呼叫号码
	 * @return
	 * @throws CtiServerException
	 */
	Response transferOne(Operator operator,String workId,String number)throws CtiServerException;
}
