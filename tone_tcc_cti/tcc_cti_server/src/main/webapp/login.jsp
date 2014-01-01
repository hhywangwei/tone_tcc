<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页</title>
<script type="text/javascript" src='<c:url value="source/js/jquery-1.4.1.min.js"/>'></script>
<style>
body {padding:0; margin:0; font-size:12px; font-family:"宋体"; background:#fff; color:#666;}

h1,h2,h3,h4,h5,h6,p,form,div,dl,dd,dt,ul,li,input,img {padding:0; margin:0; list-style-type:none; border:0; border:none;}

.clear {height:0px; overflow:hidden; clear:both;}

input {outline:none;}
a {text-decoration:none; color:#666;}
a:hover {color:#000;}

.head_box {width:900px; margin:0 auto; height:40px; padding:25px 0;}
.logo {float:left; width:420px;}

.wbimg { position:absolute; top:0; left:0;}

.login_bg {width:100%; margin:0 auto; background:url('<c:url value="/source/images/bg.gif"/>') left top repeat-x; height:500px;}
.lg_banner {width:1000px; margin:0 auto; height:500px; position:relative;}
.login { background:url('<c:url value="/source/images/dly_03.png"/>') left top no-repeat; width:250px; height:377px; position:absolute; right:50px; top:47px; padding:0 25px;}
.login h1 {height:44px; line-height:44px; font-size:14px; font-weight:normal;}

.wxts { padding:27px 0 8px 0;}
.wxts span { color:#ff3900;}

.zh input {width:237px; height:38px; line-height:38px; font-size:14px; color:#999999; background:url('<c:url value="/source/images/dly_23.gif"/>') left top no-repeat; margin-top:20px; padding:0 13px;}
.mm input {width:237px; height:38px; line-height:38px; font-size:14px; color:#999999; background:url('<c:url value="/source/images/dly_26.gif"/>') left top no-repeat; margin-top:20px; padding:0 13px;}
.yzm input {widht:135px; height:38px; line-height:38px; font-size:14px; color:#999999; margin-top:20px; background:url('<c:url value="/source/images/dly_28.gif"/>') left top no-repeat; padding:0 13px;}

.yzm {width:250px; height:38px; position:relative;}
.yzm img {position:absolute; top:20px; right:0;}

.lgal {width:250px; height:40px; position:relative; margin-top:46px;}
.login_bnt  {width:117px; height:40px; background:url('<c:url value="/source/images/login.gif"/>') left top no-repeat; position:absolute; left:0; top:0; cursor:pointer;}
.reset {width:117px; height:40px; background:url('<c:url value="/source/images/reset.gif"/>') left top no-repeat; position:absolute; right:0; top:0; cursor:pointer;}

.footer_box {width:900px; margin:0 auto; padding-top:28px; height:100px; position:relative;}
.gyyy span {padding:0 6px 0 11px; color:#bbb;}
.bqsy {position:absolute; right:0; top:28px;}

.wb_box {float:right; padding:8px 0 0 0;}
.wb_box p {float:left; height:28px; line-height:28px; overflow:hidden; cursor:pointer;}
.wb_box p a {cursor:pointer;}
.wb_box p b,.wb_box p font,.wb_box p span {float:left;}
.wb_box b {display:block; width:34px; height:28px; background-image:url('<c:url value="/source/images/wb_bg.png"/>'); background-repeat:no-repeat;}
.wb_box font {padding-left:7px; font-size:12px;}
.wb_box span {padding:0 18px; color:#bbb;}
.wb_box .p1 b {background-position:-34px 0;}
.wb_box .p2 b {background-position:-34px -28px; height:27px;}
.wb_box .p3 b {background-position:-34px -55px;}
.wb_box .p1 a:hover b {background-position:0 0;} 
.wb_box .p2 a:hover b {background-position:0 -28px;}
.wb_box .p3 a:hover b {background-position:0 -55px;}
</style>
</head>
<body>
<div class="head_box">
  <p class="logo"><img src='<c:url value="/source/images/logo.gif"/>' /></p>
  <div class="wb_box">
    <p class="p1"><a href="#"><b></b><font>官方微信</font></a><span>|</span></p>
    <p class="p2"><a href="#"><b></b><font>新浪官微</font></a><span>|</span></p>
    <p class="p3"><a href="#"><b></b><font>腾讯官微</font></a></p>
  </div>
  <p class="clear"></p>
</div>
<div class="login_bg">
  <div class="lg_banner">
    <p><img src='<c:url value="/source/images/bgtu.jpg"/>' /></p>
    <div class="login">
     <form name="form1" action="<c:url value="/sys/login/loginCheck.html"/>" method="post">
      <h1>管理员登陆</h1>
      <p class="wxts"><span>温馨提示：</span>如忘记密码请与官方联系</p>
      <p class="zh"><input id="uname" name="uname" type="text" value="账户" onfocus="if(value=='账户'){value=''}" onblur="if(value==''){value='账户'}"  /></p>
      <p class="mm"><input class="pswt" type="text" value="密码" /><input style="display:none;" class="pswh" id="upass" name="upass" type="password"></p>
      <p class="yzm"><input type="text" value="验证码" onfocus="if(value=='验证码'){value=''}" onblur="if(value==''){value='验证码'}"  /><a href="#"><img src='<c:url value="/source/images/dly_30.gif"/>' /></a></p>
      <p class="lgal">
        <input type="submit" value="" class="login_bnt"/>
        <input type="reset" value="" class="reset"/>
      </p>
     </form>
    </div>
  </div>
</div>
<div class="footer_box">
 <p class="gyyy">
  <a href="#">关于宜远<span>|</span></a>
  <a href="#">官方博客<span>|</span></a>
  <a href="#">诚征英才<span>|</span></a>
  <a href="#">开放平台<span>|</span></a>
  <a href="#">联系我们<span>|</span></a>
  <a href="#">帮助中心</a>
 </p>
 <p class="bqsy">京ICP备12045498号<span>宜远E-Tone智能呼叫中心</span>版权所有</p>
</div>
<!--密码框明文转密文JS-->
<script type="text/javascript">
jQuery(document).ready(function() {
								
	jQuery(":input[type='password']").val("");
	
	$(":input").focus(function() {
		var $obj = jQuery(this).addClass("iuib_l_hover iuib_l_focus");
		var defValue = $obj.attr("def");
		var $IU_tip = $obj.parent().siblings(".IU_tip");
		if (defValue == $obj.val()) {
			$obj.val("");
		}
		if ($obj.hasClass("pswt")) {
			$obj.hide().siblings("input.pswh").show().focus();
		}
		if ($IU_tip) {
			$IU_tip.show();
		}
	});
	
	$(":input").blur(function() {
		var $obj = jQuery(this).removeClass("iuib_l_hover");
		var $IU_tip = $obj.parent().siblings(".IU_tip");
		if ($obj.val() == "") {
			$obj.val($obj.attr("def"));
			$obj.removeClass("iuib_l_focus");
		}
		if ($obj.hasClass("pswh")) {
			if ($obj.val() == "") {
				$obj.hide().siblings("input.pswt").show();
			}
		}
		if ($IU_tip) {
			$IU_tip.hide();
		}
	});
});
</script>
<!--密码框明文转密文JS-->
</body>
</html>