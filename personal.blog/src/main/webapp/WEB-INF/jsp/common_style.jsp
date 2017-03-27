<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page" />
<link rel="shortcut icon" href="${path}/images/logo.ico">
<link rel="stylesheet" type="text/css" href="${path}/style/css/font-awesome.min.css" />
<link rel="stylesheet" href="${path}/style/jquery-ui.min.css" type="text/css" />
<link rel="stylesheet" href="${path}/style/common.css" type="text/css" />
<link rel="stylesheet" href="${path}/style/home.css" type="text/css" />
<link rel="stylesheet" href="${path}/style/article.css" type="text/css" />
<link rel="stylesheet" href="${path}/style/photo.css" type="text/css" />

<style type="text/css">
    form.pagination .button{
        behavior: url(${path}/style/pie/PIE.htc);
        position: relative;
    }
</style>
