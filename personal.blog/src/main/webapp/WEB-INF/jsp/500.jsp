<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <c:set value="${pageContext.request.contextPath}" var="path" scope="page" />
    <%-- <meta http-equiv="refresh" content="3;url=${path}" /> --%>
    <title>
        出错啦
    </title>
    <link rel="shortcut icon" href="${path}/images/logo.ico">
    <link rel="stylesheet" href="${path}/style/error.page.css"/>
</head>
<body>
<div class="center">
    <div class="content">
        <div class="label">
            <p>出错啦！</p>
        </div>
        <div class="detail">
            <p class="title">对不起，可能是网络原因或者无此页面，请稍后再试。</p>
            <p class="append">本页面3秒之后将带您回到首页</p>
        </div>
    </div>
    <div class="button">
        <div><a href="${path}">马上跳转</a></div>
    </div>
</div>
</body>
</html>
