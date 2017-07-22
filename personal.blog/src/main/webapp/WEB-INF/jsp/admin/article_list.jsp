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
</head>
<body class="admin-home-body">
	<div class="home">
		<div class="home-wrapper">
			<div class="main-menu">
				<jsp:include page="nav_menu.jsp" />
				<div class="main-content">
					<div class="main-content-wrapper" id="main-content-wrapper">
						<jsp:include page="ajax_article_list.jsp" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

