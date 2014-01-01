<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href='<c:url value="/source/left.css"/>' rel="stylesheet" type="text/css" />
</head>
<body>
<!--左侧菜单开始-->
  <div class="menu" id="tit">
    <h1><a href="#">首页</a></h1>
    <dl>  
      <dt class="offdt" onclick="javascript:show_dd(1,this);">运行记录查询</dd>
      <dd style="display:block;" id=LM1>
         <a href="#">呼入明细</a>
         <a href="#">呼出明细</a>
         <a href="#">录音查询</a>
         <a href="#">语音留言</a>
         <a href="#">员工状态</a>
      </dd>
    </dl>
    <dl>
      <dt class="opendt" onclick="javascript:show_dd(2,this);">电子传真</dt>
      <dd style="display:none;" id=LM2>
         <a href="#">发送传真</a>
         <a href="#">查看传真</a>
      </dd>
    </dl>
    <dl>   
      <dt class="offdt" onclick="javascript:show_dd(3,this);">客户关系管理</dt>
      <dd style="display:none;" id=LM3>
         <a href="#">客户列表</a>
         <a href="#">受理记录</a>
         <a href="#">发布公告</a>        
      </dd>
    </dl>

  </div>
<!--左侧菜单结束-->


<!--左侧导航菜单JS-->
<SCRIPT language=javascript>
function LMYC() {
var lbmc;
//var treePic;
    for (i=1;i<=3;i++) {
        lbmc = eval('LM' + i);
        //treePic = eval('treePic'+i);
        //treePic.src = 'images/file.gif';
        lbmc.style.display = 'none';
		
    }
}
 
function show_dd(i,obj) {
    lbmc = eval('LM' + i);
	var dts = document.getElementById("tit").getElementsByTagName("dt");
	for(var i=0;i<dts.length;i++){
		dts[i].className ="offdt";	
	}
    //treePic = eval('treePic' + i)
    if (lbmc.style.display == 'none') {
        LMYC();
        obj.className= 'opendt';
		//alert(obj.getElementsByTagName("span")[0].innerHMLT);
        lbmc.style.display = '';
    }
    else {
        obj.className= 'offdt';
        lbmc.style.display = 'none';
    }
}
//-->
		
</SCRIPT>
</body>
</html>