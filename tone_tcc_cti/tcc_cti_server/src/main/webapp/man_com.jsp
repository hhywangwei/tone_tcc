<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<TITLE>上海宜远信息科技有限公司</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META http-equiv=cache-control content=no-cache>
<META http-equiv=pragma content=no-cache>
<link href='<c:url value="/source/style.css"/>' rel="stylesheet" type="text/css" />
</head>
<body class="body">
<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
  <tr>
    <td colspan="2" height="76">
      <iframe src='<c:url value="top.jsp"/>' frameborder="0" scrolling="no" height="76" width="100%"></iframe>
    </td>
  </tr>
  <tr>
    <td valign="top" width="100%">
       <table width="1000" cellpadding="0" align="center" cellspacing="0" border="0" height="100%">
         <tr>
           <td valign="top" height="100%" width="170" style="padding:10px 10px 0 0;">
              <table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
                <tr>
                  <td height="5"><img src='<c:url value="/source/images/top_yj.gif"/>' /></td>
                </tr>
                <tr>
                  <td height="100%" class="left_bg">
                     <iframe src='<c:url value="left.jsp"/>' frameborder="0" scrolling="no" width="170" height="100%" allowTransparency="true"></iframe>
                  </td>
                </tr>
                <tr>
                  <td height="5"><img src='<c:url value="/source/images/bot_yj.gif"/>' /></td>
                </tr>
              </table>
           </td>
           <td width="820" valign="top" height="100%" style="padding-top:10px;">
              <iframe src='<c:url value="right.jsp"/>' frameborder="0" scrolling="auto" height="100%" width="100%" allowTransparency="true"></iframe>
           </td>
         </tr>
       </table>
    </td>
  </tr> 
  <tr>
    <td colspan="2" height="40">
      <p class="footr">Copyright&copy; 2012 - 2013 Ewin Inc. All Rights Reserved<span>上海宜远信息科技有限公司 版权所有</span></p>
    </td>
  </tr>    
</table>
</body>
</html>
