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

<style>
.customer-info-list table thead th.title {
    width: 440px;
}

.customer-info-list table thead th.type {
    width: 120px;
}

.customer-info-list table thead th.operation {
    width: 100px;
}

.customer-info-list {
    text-align: left;
}
</style>

<%@include file="../common_javascript.jsp"%>
<%@include file="../common_style.jsp"%>
</head>
<body class="admin-home-body">
    <div class="home">
        <div class="home-wrapper">
            <div class="main-menu">
                <jsp:include page="nav_menu.jsp" />
                <div class="main-content">
                    <div class="main-content-wrapper"
                        id="main-content-wrapper">
                        <div class="customer-info-list">
                            <h3>文章类型列表</h3>
                            <table width="100%" height="100%" class="table">
                                <thead>
                                    <th class="title">类型名称</th>
                                    <th class="type">类型下文章数量</th>
                                    <th class="operation">操作</th>
                                </thead>
                                <tbody>
                                    <c:forEach items="${typeList}"
                                        var="list">
                                        <tr>
                                            <td><div>
                                                    <span>${list.typeName }</span>
                                                </div></td>
                                            <td><div>
                                                    <span>${list.typeCount }</span>
                                                </div></td>
                                            <td><a onclick="Event.openInCurrentTab('/admin/type/add/article-type?typeId=${list.typeId}');"
                                                href="javascript:void(0);">编辑</a>&nbsp;
                                                <a onclick="App.deleteArticleTypeById(this,'${list.typeId}');"
                                                href="javascript:void(0);">删除</a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

