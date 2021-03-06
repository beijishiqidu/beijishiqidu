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
.main-content{
    padding-bottom: 100px;
}

.photo-detail-info-list{
    width: 1200px;
    overflow:hidden;
}

.bg-photo-detail-container{
    width: 1215px;
}

.bg-photo-detail-container .item{
    float: left;
    margin-right: 15px;
    width: 390px;
    height: 300px;
    overflow: hidden;
    border: 1px solid #999;
    margin-bottom: 20px;
}

.bg-photo-detail-container .zoom-image {
    width:100%;
    height:0;
    padding-bottom: 100%;
    overflow:hidden;
    background-position: center center;
    background-repeat: no-repeat;
    -webkit-background-size:cover;
    -moz-background-size:cover;
    background-size:cover;
}

.bg-photo-detail-container .delete-layer{
    border-bottom: 1px solid #666;
    padding: 1px 0 1px 1px;
}

.bg-photo-detail-container .vertial-line{
    height: 15px;
}

</style>

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
                    <div class="main-content-wrapper"
                        id="main-content-wrapper">
                        <div class="photo-detail-info-list">
                            <h1>相册编辑</h1>
                            
                            <div class="page-header">
                              <h4 style="float: left;">相册名称：</h4><h4 style="float: left;">${photoAlbum.title}</h4><div class="cl"></div>
                              <h4 style="float: left;">相册类型：</h4><h4 style="float: left;">${photoAlbum.type.typeName}</h4><div class="cl"></div>
                            </div>
                            
                            <div class="bg-photo-detail-container">
                                <c:forEach items="${pagination.items}"
                                    var="list" varStatus="status">
                                    <div class="item">
                                        <div class="delete-layer">
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-default">查看</button>
                                                <button type="button" class="btn btn-default" onclick="App.deleteCurrentPhoto(this,'${list.id}')">删除</button>
                                            </div>
                                        </div>
                                        <div class="zoom-image" style="background-image:url(${list.urlPath})"></div>
                                    </div>
                                </c:forEach>
                                <div class="cl"></div>
                            </div>
                            <form id="articleAddForm" method="post" target="hideframe"
                            action="<c:url value="/admin/photo/save"/>">
                                <input type="hidden" name="photoAlbumId" value="${photoAlbum.id}" />
                                <input type="hidden" name="title" value="${photoAlbum.title}" />
                                <input type="hidden" name="type" value="${photoAlbum.type.id}" />
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
    </div>
</body>

<script type="text/javascript">
    $(function(){
        
    });
</script>

</html>

