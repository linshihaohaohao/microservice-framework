<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<%@include file="/common/taglibs.jsp"%>
<%
	Object isPre = (Object)request.getAttribute("isPre");
    String pre = "";
	if(isPre != null){
		pre = isPre.toString();
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="${root}/content/ui/css/layout.css" type="text/css" rel="stylesheet" />
    <link href="${root}/content/ui/css/login.css" type="text/css" rel="stylesheet"/>
    <style>
        .ibar {
            display: none;
        }
    </style>
</head>
<body class="login-bg">
<div class="main ">
    <!--登录-->
    <div class="login-dom login-max">
        <div class="logo text-center">
            <a href="#">
                <img src="${root}/content/ui/img/logo.png" width="180px" height="180px" />
            </a>
        </div>
        <div class="login container " id="login">
            <p class="text-big text-center logo-color">
	              B2B商城管理后台
            </p>
            <form class="login-form" action="" method="post" autocomplete="off">
                <div class="login-box border text-small" id="box">
                    <div class="name border-bottom">
                        <input type="text" placeholder="账号" id="username" value="sysadmin" name="username" datatype="*" nullmsg="请填写帐号信息" />
                    </div>
                    <div class="error_text">
					<span id="userText" class="span-css"></span>
					</div>
                    <div class="pwd">
                        <input type="password" placeholder="密码" datatype="*" id="password" value="qq123123" name="password" nullmsg="请填写帐号密码" />
                    </div>
                    <div class="error_text">
					<span id="passText" class="span-css"></span>
					</div>	
                </div>
                <!-- <input type="submit" class="btn text-center login-btn" onclick="onLogin()" value="立即登录" /> -->
                <button type="button" class="btn text-center login-btn" id="loginBtn" onclick="onLogin()" >立即登录</button>	
                <security:authorize URL="qqq">  
    <a href="admin.jsp">进入admin页面</a>  
</security:authorize>  
  
<security:authorize URL="">  
    <a href="admin.jsp">进入admin页面</a>  
</security:authorize> 
            </form>
            <div class="forget">

                <a href="http://angelapi.bluemoon.com.cn/bluemoonMana/" class="forget-pwd text-small fl"> 忘记登录密码？</a><!-- <a href="#" class="forget-new text-small fr" id="forget-new">注册账号</a> -->
            </div>
        </div>
    </div>
    <div class="footer text-center text-small ie">
        Copyright 2013-2016 版权所有 ©tzhsweet 2015-2018      <a href="#" target="_blank">粤ICP备16024545号-1</a>
        <span class="margin-left margin-right">|</span>
    </div>
    <div class="popupDom">
        <div class="popup text-default">
        </div>
    </div>
</div>
</body>

<%@include file="/common/js_common.jsp"%>
<script src="${root}/js/md5_32.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			$("#loginBtn").click();//处理事件
		}
	}
});
var pre = "<%=pre%>";
if(pre == "1"){
	alert('未登录或登录超时。请重新登录，谢谢！');
	top.location.href="${root}/toLogin";
}
    function popup_msg(msg) {
        $(".popup").html("" + msg + "");
        $(".popupDom").animate({
            "top": "0px"
        }, 400);
        setTimeout(function () {
            $(".popupDom").animate({
                "top": "-40px"
            }, 400);
        }, 2000);
    }
    
    function dataValidate(){
		document.getElementById("userText").innerHTML = "";
		document.getElementById("passText").innerHTML = "";
		//document.getElementById("randText").innerHTML = "";
		var flag = "";
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		//var rand = document.getElementById("rand").value;
		
		if(username == "" || username == null ){
			flag = "用户名不能为空";
			document.getElementById("userText").innerHTML = flag;
		}
		if( password == "" || password == null ){
			flag = "密码不能为空";
			document.getElementById("passText").innerHTML = flag;
		}else{
			var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,18}$/;
			if( !re.test(password) ){
				flag = "密码必须由8-18位数字和字母组合，如不符合安全要求请通过忘记密码重新设置！";
				alert(flag);
			}
		}
		/* if(rand == "" || rand == null ){
			flag = "验证码不能为空";
			document.getElementById("randText").innerHTML = flag;
		} */

		return flag;
	}
    
  //使用ajax的方式提交登录信息，然后通过json数据格式将返回结果返回
	function onLogin(){
		var flag = dataValidate();
		if(flag == "" || flag == null ){
			var username = document.getElementById("username").value;
			var password = document.getElementById("password").value;
			password = hex_md5(password);
			//var rand = document.getElementById("rand").value;
			//var params= {account:username,password:password,rand:rand};
			var params= {account:username,password:password};
			params = JSON.stringify(params);
			$.ajax({
		         type: 'POST',
		         url: '${root}/login',
		         dataType: "json",
        	 	 contentType:"application/json",
		         data:params,
		         success:function(data){
			         var isSuccess = data.isSuccess; 
			         if( isSuccess == true ){
			        	 window.location.href="${root}/index";
			         }else{
			         	alert(data.responseMsg);
			         	//reloadcode();
			         } 
			     }
		     });   
		}
	}
  
	function reloadcode(){
		var verify=document.getElementById('safecode');
		verify.setAttribute('src','js/valiCodeImg.jsp?'+Math.random());
		//这里必须加入随机数不然地址相同我发重新加载
	}
</script>
</html>
