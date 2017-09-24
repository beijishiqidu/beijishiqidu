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
<%@include file="../common_javascript.jsp"%>

<link rel="stylesheet" type="text/css" href="${path}/style/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${path}/webfileupload/css/syntax.css" />
<link rel="stylesheet" type="text/css" href="${path}/webfileupload/css/style.css" />
<link rel="stylesheet" type="text/css" href="${path}/webfileupload/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${path}/webfileupload/css/demo.css" /> 
<%@include file="../common_style.jsp"%>

<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body class="admin-home-body">
    <div class="home">
        <div class="home-wrapper">
            <div class="main-menu">
                <jsp:include page="nav_menu.jsp" />
                <div class="main-content">
                    <h3>添加相片</h3>
                    <div class="main-content-wrapper"
                        id="main-content-wrapper">
                        <input type="hidden" value="${path}" id="path">
                        <form id="articleAddForm" method="post"
                            
                            target="hideframe"
                            action="<c:url value="/admin/photo/save"/>">
                            <input name="articleId" id="photoId"
                                value="${articleObj.id}" type="hidden" />
                            <div class="title-container">
                                <label>相册标题:</label><input name="title"
                                    class="title" type="text"
                                    value="${articleObj.title}" /> <span
                                    data-name="title" class="error-tips"></span>
                            </div>
                            <div class="title-container">
                            <label class="case-description">相册:</label>
                                <select id="type" name="type" style="width: 1200px;height: 30px;">
                                    <option value="">请选择相册</option>
                                    <c:forEach items="${photoTypeList}" var="list">
                                        <c:set var="articleTypeSelected" value="${articleObj.type.id==list.id?'selected=\"selected\"':''}"/>
                                        <option value="${list.id}" ${articleTypeSelected}>${list.typeName}</option>
                                    </c:forEach>
                                </select>
                            <span data-name="type" class="error-tips"></span>
                            </div>
                            <!-- 加载编辑器的容器 -->
                            <label>上传相片列表:</label>
                            
                            <div id="uploader" class="wu-example">
                                <div class="queueList">
                                    <div id="dndArea" class="placeholder">
                                        <div id="filePicker"></div>
                                        <p>或将照片拖到这里，单次最多可选300张</p>
                                    </div>
                                </div>
                                <div class="statusBar" style="display:none;">
                                    <div class="progress">
                                        <span class="text">0%</span>
                                        <span class="percentage"></span>
                                    </div><div class="info"></div>
                                    <div class="btns">
                                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                                    </div>
                                </div>
                            </div>
                            
                            <span data-name="content" class="error-tips"></span>
                            <div class="button-area">
                                <div id="resultMsg" class=""></div>
                                <button class="button" type="button"
                                    onclick="App.submitPhotoInfo();">保存</button>
                                <div class="cl"></div>
                            </div>
                        </form>
                        <iframe name="hideframe" id="hideframe" style="display:none"></iframe>
                        <script type="text/javascript" src="${path}/javascript/lib/01-jquery-1.11.3.min.js"></script>
                        <script type="text/javascript" src="${path}/webfileupload/js/05-bootstrap.min.js"></script>
                        <script type="text/javascript" src="${path}/webfileupload/js/webuploader.js"></script>
                        <script type="text/javascript" src="${path}/webfileupload/js/demo.js"></script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
