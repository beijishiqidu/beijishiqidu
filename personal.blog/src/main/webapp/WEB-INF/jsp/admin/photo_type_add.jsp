<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>北极十七度网站后台管理首页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<c:set value="${pageContext.request.contextPath}" var="path"
    scope="page" />

<%@include file="../common_style.jsp"%>
<%@include file="../common_javascript.jsp"%>

<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body class="admin-home-body">
    <div class="home">
        <div class="home-wrapper">
            <div class="main-menu">
                <jsp:include page="nav_menu.jsp" />
                <div class="main-content">
                    <h3>添加相册类型</h3>
                    <div class="main-content-wrapper"
                        id="main-content-wrapper">
                        <form id="articleTypeAddForm" method="post"
                            target="hideframe"
                            action="<c:url value="/admin/type/add/save-photo-type"/>">
                            <input name="typeId" id="typeId"
                                value="${typeObj.id}" type="hidden" />
                            <div class="title-container">
                                <label>类型名称:</label><input name="typeName"
                                    class="title" type="text"
                                    value="${typeObj.typeName}" /> <span
                                    data-name="typeName" class="error-tips"></span>
                            </div>
                            <span data-name="content" class="error-tips"></span>
                            <div id="resultMsg" class=""></div>
                            <div class="button-area">
                                <button class="button" type="button"
                                    onclick="App.submitArticleTypeInfo();">保存</button>
                                <div class="cl"></div>
                            </div>
                        </form>
                        <iframe name="hideframe" id="hideframe"
                            style="display:none"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
