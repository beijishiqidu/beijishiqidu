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
<link rel="shortcut icon" href="${path}/images/logo.ico">
<link rel="stylesheet" href="${path}/style/css/font-awesome.min.css" />
<link rel="stylesheet" href="${path}/style/app/common.css" />
<link rel="stylesheet" href="${path}/style/app/background.css" />
<link rel="stylesheet" href="${path}/style/app/admin_home_nav_menu.css" />
<script type="text/javascript" src="${path}/javascript/lib/01-jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${path}/javascript/app/app.js"></script>
<script type="text/javascript" src="${path}/javascript/app/event.js"></script>
<script type="text/javascript" src="${path}/javascript/app/top-menu.js"></script>
<script type="text/javascript">
    $(function() {
        App.setUrlPath("${path}");
    })
</script>
</head>
<body class="admin-home-body">
	<div class="home">
		<div class="home-wrapper">
			<div class="main-menu">
				<jsp:include page="nav_menu.jsp"/>
				<div class="main-content">
					<div class="main-content-wrapper" id="main-content-wrapper">
						<h3>欢迎来到北极十七度网站后台管理系统</h3>
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
