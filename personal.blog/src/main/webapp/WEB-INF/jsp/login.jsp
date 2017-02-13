<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
<title>管理员登陆</title>
<c:set value="${pageContext.request.contextPath}" var="path"
	scope="page" />
<link rel="shortcut icon" href="${path}/images/logo.ico"/>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>

<link rel="stylesheet" href="${path}/style/common.css" />
<link rel="stylesheet" href="${path}/style/background.css" />

<script type="text/javascript" src="${path}/javascript/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${path}/javascript/app.js"></script>
<style type="text/css">
    #resultMsg{
        float: none;
        text-align: left;
        padding-left: 25px;
        font-size: 14px;
    }
</style>
</head>
<body class="bg-color">
	<div class="center form">
		<form action="/login.do" method="post" id="loginForm" class="login">
			<div class="title">用户登陆</div>
			<div class="field">
				<input type="text" id="userName" name="userName" placeholder="用户名"/>
				<img src="images/login_user.jpg"/>
			</div>
			<div class="field">
				<input type="password" id="passWord" name="passWord" placeholder="密码" />
				<img src="images/login_pwd.jpg"/>
			</div>
			<div class="submit-container">
				<div class="submit" onclick="App.Login('${path}');">登陆</div>
			</div>
			<div id="resultMsg" class="error"></div>
		</form>
	</div>
<script type="text/javascript">
    $(function(){
        App.setUrlPath("${path}");
    });
</script>
</body>
</html>
