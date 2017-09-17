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
                    <div class="main-content-wrapper"
                        id="main-content-wrapper">
                        <div class="photo-detail-info-list">
                            <h1>相册详细</h1>
                            
                            <div class="page-header">
                              <h4 style="float: left;">相册名称：</h4><h4 style="float: left;">${photoAlbum.title}</h4><div class="cl"></div>
                              <h4 style="float: left;">相册类型：</h4><h4 style="float: left;">${photoAlbum.type.typeName}</h4><div class="cl"></div>
                            </div>
                            
                            <div class="bg-photo-detail-container">
                                <c:forEach items="${pagination.items}"
                                    var="list" varStatus="status">
                                    <div class="item">
                                        <div class="zoom-image" style="background-image:url(${list.urlPath})"></div>
                                    </div>
                                </c:forEach>
                                <div class="cl"></div>
                            </div>
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

