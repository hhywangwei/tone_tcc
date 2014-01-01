<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href='<c:url value="/source/style.css"/>' rel="stylesheet" type="text/css" />
</head>
<body>
<div class="header_box">
  <div class="header">
      <p class="logo"><img src='<c:url value="/source/images/logo.png"/>' /></p>
      <div class="kzmb">
      <p class="gly">欢迎<b>LI Hong Wei</b>管理员<span><a href="#">[修改信息]</a></span><span><a href="#">[重读配置]</a></span>
      <span><a href="<c:url value="/sys/login/logout.html"/>">[退出]</a></span></p>
      <ul class="nav">
        <li class="on"><a href="#">控制面板</a></li>
        <li><a href="#">快捷菜单</a></li>
        <li><a href="#">快捷菜单</a></li>
        <li><a href="#">快捷菜单</a></li>
        <li><a href="#">快捷菜单</a></li>
      </ul>
      <p class="clear"></p>
    </div>
    <p class="clear"></p>
  </div>
</div>
</body>
</html>