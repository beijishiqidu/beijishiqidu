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
                    <h3>添加文章</h3>
                    <div class="main-content-wrapper"
                        id="main-content-wrapper">
                        <!-- enctype="multipart/form-data" -->
                        <form id="articleAddForm" method="post"
                            target="hideframe"
                            action="<c:url value="/admin/article/save"/>">
                            <input name="articleId" id="articleId"
                                value="${articleObj.id}" type="hidden" />
                            <div class="title-container">
                                <label>文章标题:</label><input name="title"
                                    class="title" type="text"
                                    value="${articleObj.title}" /> <span
                                    data-name="title" class="error-tips"></span>
                            </div>
                            <div class="title-container">
                            <label class="case-description">文章类型:</label><select id="type" name="type" style="width: 1200px;height: 30px;">
                                    <option value="">请选择文章类型</option>
                                    <c:forEach items="${articleTypeList}" var="list">
                                        <c:set var="articleTypeSelected" value="${articleObj.type.id==list.id?'selected=\"selected\"':''}"/>
                                        <option value="${list.id}" ${articleTypeSelected}>${list.typeName}</option>
                                    </c:forEach>
                                </select>
                            <span data-name="type" class="error-tips"></span>
                            </div>
                            <!-- 加载编辑器的容器 -->
                            <label>详细内容:</label>
                            <script id="container" name="content" type="text/plain"></script>
                            <span data-name="content" class="error-tips"></span>
                            <div class="cl"></div>
                            <div class="button-area">
                                <div id="resultMsg" class=""></div>
                                <button class="button" type="button"
                                    onclick="App.submitArticleInfo();">保存</button>
                                <div class="cl"></div>
                            </div>
                        </form>
                        <iframe name="hideframe" id="hideframe" style="display:none"></iframe>
                        <!-- 配置文件 -->
                        <script type="text/javascript" src="${path}/ueditor.config.js"></script>
                        <!-- 编辑器源码文件 -->
                        <script type="text/javascript" src="${path}/ueditor.all.js"></script>
                        <!-- 实例化编辑器 -->
                        <script type="text/javascript">
                            var editor = UE.getEditor('container');
                            var content = '${articleObj.content}';
                            editor.ready(function() {editor.setContent(content);
                            });
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
