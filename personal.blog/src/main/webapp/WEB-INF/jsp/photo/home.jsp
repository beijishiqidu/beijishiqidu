<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="../common_meta_link.jsp"%>
<%@include file="../common_style.jsp"%>
<%@include file="../common_javascript.jsp"%>
<title>北极十七度的个人主页</title>
</head>
<body>
    <%@include file="../inner-header.jsp"%>
    <div class="photo-home-body-wapper">
        <div class="photo-home-body">
            <a href="${path}/photo/list"><img src="/images/p1.jpg"></a>
        </div> 
    </div>
    <%@include file="../footer.jsp"%>
</body>
</html>
