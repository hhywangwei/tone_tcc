<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href='<c:url value="source/style.css"/>' rel="stylesheet" type="text/css" />
<script type="text/javascript" src='<c:url value="source/js/jquery-1.4.2.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="source/js/jquery.tzCheckbox.js"/>'></script>
<script type="text/javascript" src='<c:url value="source/js/jquery.tzCheckbox.js"/>'></script>
</head>
<body>
<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
  <tr>
    <td valign="top">
          <p class="kjcd2">坐席首页</p>
          <div class="bd_box">
            <h1 class="h1_tit">坐席信息</h1>
            <table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%" class="table_1">
              <tr>
                <td width="30%">工号：8001</td>
                <td width="40%">号码：6001</td>
                <td>所属分组：故障受理、咨询</td>
              </tr>
              <tr>
                <td>
                  <div style="position:relative;">
                		工作状态：
                    <div class="gzzt_2" onMouseOver="this.className='gzzt_1'" onMouseOut="this.className='gzzt_2'">就绪
                      <ul class="zslk">
                         <li><a href="#">暂时离开</a></li>
                         <li><a href="#">听录音</a></li>
                         <li><a href="#">培训</a></li>
                         <li><a href="#">示忙</a></li>
                         <li><a href="#">练习</a></li>
                         <li style="border-bottom:none;"><a href="#">就绪</a></li>
                      </ul>
                    </div>   
                  </div>             
                </td>
                <td>
                  <div style="position:relative;">
                   	 移动坐席状态：
                    <div class="wsz_2" onMouseOver="this.className='wsz_1'" onMouseOut="this.className='wsz_2'">未设置
                       <div class="zshm_box">
                         <form id="form1" name="form1" action="<c:url value="/sys/login/loginCTI.html"/>">
                           <p class="zxhm">坐席号码：<input id="seatnumber" name="seatnumber" type="text" /></p>
                           <p class="szqxal"><input type="submit" value="设置" class="szal"/><input type="reset" value="取消" class="qxal"/></p>
                         </form>
                       </div>
                    </div>  
                  </div>                
                </td>
                <td></td>
              </tr>              
            </table>
            <h1 class="h1_tit bd">通话信息</h1>
            <div class="ychz">
              <ul class="thxx">
                <li style="width:22%">来电号码：8001</li>
                <li>状态：<img src='<c:url value="/source/images/zxsy_06.gif"/>' class="zenglin" /></li>
                <li>静音：<input type="checkbox" style="display:none;" name="checkbox_bg" checked="checked" /></li>
                <li>保持：<input type="checkbox" style="display:none;" name="checkbox_bg" /></li>
              </ul>
              <p class="clear"></p>
           </div>
           <!--图文菜单开始-->
           <div class="pic_menu">
              <div class="jy_box"><input type="checkbox" style="display:none;" name="checkbox_bg2" /></div>
              <div class="bc_box"><input type="checkbox" style="display:none;" name="checkbox_bg3" /></div>
                
             <div class="tcc_div">
               <h2 class="pz3"><p><img src='<c:url value="/source/images/zxsy_29.gif"/>' /></p>求助</h2>
               
               <!--求助内容开始-->
               <div class="tcc_text">
               
                 <div class="jt_box">
                   <img src='<c:url value="/source/images/zxsy_44.gif"/>' class="jtimg1" />
                   <table cellpadding="0" cellspacing="0" width="100%" border="0" class="qz_table1">  
                     <tr>
                       <td valign="top" class="td_bg" width="100">
                          <ul class="xzzq">
                           <LI id="ti_1" class="now_hover" onClick="lihw(1);">故障受理</LI>
                           <LI id="ti_2" class="old_hover" onClick="lihw(2);">咨询</LI>
                           <LI id="ti_3" style="border:none;" class="old_hover" onClick="lihw(3);">投拆</LI>
                          </ul>                         
                       </td>
                       <td valign="top" style="padding:2px 12px 12px 12px;">
                         <h2 class="close_img"><img src='<c:url value="/source/images/close_on.gif"/>' 
                         onmouseover="this.src='<c:url value="/source/images/close_off.gif"/>'" 
                         onmouseout="this.src='<c:url value="/source/images/close_on.gif"/>'" /></h2>
                         <div class="xingmin" id="tj_1">   
                           <table cellpadding="0" cellspacing="1" bgcolor="#dddddd" width="100%" border="0" class="table_2">
                             <tr>
                               <th width="107" style="border-left:none;"">姓名</th>
                               <th width="111">工号</th>
                               <th width="115">电话号码</th>
                               <th width="120">工作状态</th>
                             </tr>
                             <tr>
                               <td>语蝶</td>
                               <td>001007</td>
                               <td>13699290188</td>
                               <td>克苦、认真</td>
                             </tr>
                             <tr>
                               <td>香巧</td>
                               <td>001007</td>
                               <td>13699290188</td>
                               <td>顽强、奋斗</td>
                             </tr>              
                             <tr>
                               <td>三宝</td>
                               <td>001007</td>
                               <td>13699290188</td>
                               <td>克苦、认真</td>
                             </tr>              
                           </table>
                           <div class="sgail">
                             <input type="submit" value="求助坐席" class="qzzx" />
                             <input type="submit" value="求助场外" class="qzzx" />
                             <input type="text" class="wbk"/>
                           </div>
                         </div>  

                         <div class="xingmin" id="tj_2" style="display:none;">222</div>
                         <div class="xingmin" id="tj_3" style="display:none;">333</div>                        
                                              
                       </td>
                     </tr>
                   </table>
                 </div>
               </div>
               <!--求助内容结束-->
               </div>
             <div class="tcc_div">
               <h2 class="pz4"><p><img src='<c:url value="/source/images/zxsy_31.gif"/>' /></p>转接</h2>
               <!--转接内容开始-->
               <div class="tcc_text">
                 <div class="jt_box">
                   <img src='<c:url value="/source/images/zxsy_44.gif"/>' class="jtimg2" />
                   <table cellpadding="0" cellspacing="0" width="100%" border="0" class="qz_table1">  
                     <tr>
                       <td valign="top" class="td_bg" width="100">
                         <ul class="xzzq">
                            <LI id="ti2_1" class="now_hover" onClick="lihw2(1);">故障受理</LI>
                            <LI id="ti2_2" class="old_hover" onClick="lihw2(2);">咨询</LI>
                            <LI id="ti2_3" style="border:none;" class="old_hover" onClick="lihw2(3);">投拆</LI>
                         </ul>                        
                       </td>  
                       <td valign="top" style="padding:2px 12px 12px 12px;">
                         <h2 class="close_img"><img src='<c:url value="/source/images/close_on.gif"/>'
                         onmouseover="this.src='<c:url value="/source/images/close_off.gif"/>'" 
                         onmouseout="this.src='<c:url value="/source/images/close_on.gif"/>'" /></h2>
                         <div class="xingmin" id="tj2_1">   
                           <table cellpadding="0" cellspacing="1" bgcolor="#dddddd" width="100%" border="0" class="table_2">
                             <tr>
                               <th width="107" style="border-left:none;"">姓名</th>
                               <th width="111">工号</th>
                               <th width="115">电话号码</th>
                               <th width="120">工作状态</th>
                             </tr>
                             <tr>
                               <td>语蝶</td>
                               <td>001007</td>
                               <td>13699290188</td>
                               <td>克苦、认真</td>
                             </tr>
                             <tr>
                               <td>香巧</td>
                               <td>001007</td>
                               <td>13699290188</td>
                               <td>顽强、奋斗</td>
                             </tr>              
                             <tr>
                               <td>三宝</td>
                               <td>001007</td>
                               <td>13699290188</td>
                               <td>克苦、认真</td>
                             </tr>              
                           </table>
                           <div class="sgail">
                             <input type="submit" value="转接坐席" class="qzzx" />
                             <input type="submit" value="转接到组" class="qzzx" />
                           </div>
                         </div>
                  
                         <div class="xingmin" id="tj2_2" style="display:none;">222</div>
                         <div class="xingmin" id="tj2_3" style="display:none;">333</div>                 
                         
                       </td>
                     </tr>
                   </table>                     
                 </div>            
               </div>
               <!--转接肉容结束-->
             </div>    
                
                
             <div class="tcc_div">
               <h2 class="pz5"><p><img src='<c:url value="/source/images/zxsy_33.gif"/>' /></p>外呼</h2>
               
               <!--外呼内容开始-->
               <div class="tcc_text">
                 <div class="jt_box">
                   <img src='<c:url value="/source/images/zxsy_44.gif"/>' class="jtimg3" />
                   
                   <table cellpadding="0" cellspacing="0" width="100%" border="0" class="qz_table1">  
                     <tr>
                       <td valign="top" style="padding:2px 12px 12px 12px;">
                         <h2 class="close_img"><img src='<c:url value="/source/images/close_on.gif"/>'
                         onmouseover="this.src='<c:url value="/source/images/close_off.gif"/>'" 
                         onmouseout="this.src='<c:url value="/source/images/close_on.gif"/>'" /></h2>              
                           <table cellpadding="0" cellspacing="1"  bgcolor="#dddddd" border="0" width="100%" class="table_2">
                             <tr>
                               <th>姓名</th>
                               <th>工号</th>
                               <th>呼叫次数</th>
                               <th>呼叫状态</th>
                               <th>接通时间</th>
                               <th>通话里长</th>
                               <th>操作</th>
                             </tr>
                             <tr>
                               <td>语蝶</td>
                               <td>001</td>
                               <td>2</td>
                               <td>通话结束</td>
                               <td>1:20</td>
                               <td>1:20</td>
                               <td>无</td>
                             </tr>
                             <tr>
                               <td>语蝶</td>
                               <td>001</td>
                               <td>2</td>
                               <td>通话结束</td>
                               <td>1:20</td>
                               <td>1:20</td>
                               <td>无</td>
                             </tr>
                             <tr>
                               <td>语蝶</td>
                               <td>001</td>
                               <td>2</td>
                               <td>通话结束</td>
                               <td>1:20</td>
                               <td>1:20</td>
                               <td>无</td>
                             </tr>
                           </table>
                           <div class="sgpbq">
                             <p class="dhhm"><span>输入号码</span><input type="text"/></p>
                             <p><input type="submit" value="单个呼叫" class="dgfj"/></p>
                             <p><input type="submit" value="发起群呼" class="dgfj"/></p>
                             <p><input type="submit" value="上传名单" class="dgfj"/></p>
                           </div>
                       </td>
                     </tr>
                   </table>
                 </div>         
               </div>            
               
               <!--外呼内容结束-->  
               </div>
               
                
               <h1 class="pz6" style="width:62px;"><p><img src='<c:url value="/source/images/zxsy_35.gif"/>' /></p>转自动语音</h1>
      
               <p class="clear"></p>
                
            </div>
            <!--图文菜单结束-->
              
      </div>
      <script>
		$(document).ready(function(){
		$('input[name=checkbox_bg]').tzCheckbox({labels:['Enable','Disable']});
		$('input[name=checkbox_bg2]').tzCheckbox({labels:['静音','取消静音']});
		$('input[name=checkbox_bg3]').tzCheckbox({labels:['保持','取消保持']});
		});	
      </script>      
      <SCRIPT>
      /*左侧菜单点击*/
      $(".pic_menu").delegate("h2","click",function(){
          var _thisMeneDetail = $(this).next(".tcc_text")
              ,_menuList = $(this).parents(".tcc_div");
          _menuList.addClass("cur").siblings(".tcc_div").removeClass("cur");
          if(_thisMeneDetail.is( ":visible")){
              _menuList.removeClass("cur");
              _thisMeneDetail.slideUp();
              return false;
          }else{
              $(".tcc_text",".tcc_div").slideUp();
              _thisMeneDetail.slideDown();
              return false;
          }
      });
      /*左侧菜单*/
      
      </SCRIPT>
        
      <SCRIPT language="javascript" type="text/javascript">
          
          function lihw(ix)
          {
            for (i=1;i<4;i++)
            {
                if (i==ix)
                {
                    document.getElementById('ti_'+i).className = 'now_hover';
                    document.getElementById('tj_'+i).style.display = 'block';
                }
                else
                {
                    document.getElementById('ti_'+i).className = 'old_hover'; 
                    document.getElementById('tj_'+i).style.display = 'none'; 
                }
            }
          
          }	
          
          function lihw2(ix)
          {
            for (i=1;i<4;i++)
            {
                if (i==ix)
                {
                    document.getElementById('ti2_'+i).className = 'now_hover';
                    document.getElementById('tj2_'+i).style.display = 'block';
                }
                else
                {
                    document.getElementById('ti2_'+i).className = 'old_hover'; 
                    document.getElementById('tj2_'+i).style.display = 'none'; 
                }
            }
          
          }	
      </SCRIPT>
    
    
    </td>
    
    
    <td valign="top" width="200" style="padding-left:10px;">
      <div class="right">
        <h1>今日提醒</h1>
        <div class="jrtx">
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
          <p><a href="#">最新公告最新公告最新公告新</a></p>
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
          <p><a href="#">最新公告最新公告最新公告新</a></p>
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
          <p><a href="#">最新公告最新公告最新公告新</a></p>
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
        </div>
        <p style="margin-bottom:10px;"><img src='<c:url value="/source/images/htsy_42.gif"/>'  /></p>
        
        <h1>最新公告</h1>
        <div class="jrtx">
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
          <p><a href="#">最新公告最新公告最新公告新</a></p>
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
          <p><a href="#">最新公告最新公告最新公告新</a></p>
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
          <p><a href="#">最新公告最新公告最新公告新</a></p>
          <p class="on"><a href="#">最新公告最新公告最新公告新</a></p>
        </div>
        <p><img src='<c:url value="/source/images/htsy_42.gif"/>' /></p>
      </div>

    </td>
  </tr>
</table>  
</body>
</html>