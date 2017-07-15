<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page" />
<c:set value="${releaseVersion }" var="version" scope="request"/>
<link rel="shortcut icon" href="${path}/images/logo.ico"/>
<link rel="stylesheet" type="text/css" href="${path}/style/css/font-awesome.min.css" />

<c:choose>
    <c:when test="${localEnv}">
        <link rel="stylesheet" type="text/css" href="${path}/style/lib/jquery-ui.min.css?version=${version}" />
        <c:forEach items="${cssAppFiles}" var="file">
            <c:choose>
                <c:when test="${testIe8Env}">
                    <link rel="stylesheet" type="text/css" href="${path}/style/app/${file.name}?version=${version}" />
                </c:when>
                <c:otherwise>
                    <link rel="stylesheet/less" href="${path}/style/app/${file.name}?version=${version}" />
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <link rel="stylesheet" type="text/css" href="${path}/compressed/lib-min.css?version=${version}" />
        <link rel="stylesheet" type="text/css" href="${path}/compressed/app-min.css?version=${version}" />
    </c:otherwise>
</c:choose>

<style type="text/css">
    form.pagination .button{
        behavior: url(${path}/style/pie/PIE.htc);
        position: relative;
    }
</style>
