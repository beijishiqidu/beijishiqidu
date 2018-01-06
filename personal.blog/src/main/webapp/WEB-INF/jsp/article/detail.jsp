<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="navigation" uri="/WEB-INF/navigation.tld"%>
<head>
<%@include file="../common_meta_link.jsp"%>
<%@include file="../common_style.jsp"%>
<%@include file="../common_javascript.jsp"%>
<title>北极十七度的个人主页</title>
</head>
<body>
    <%@include file="../header.jsp"%>
    <div class="body-container">
        <div class="current_location">当前位置：<navigation:conf index="${index}"/>
        </div>
        <div class="detail-content-container">
            <div class="article-item">
                    <p class="title">${articleObj.title}</p>
                    <div class="ap-area">
                        <div class="article-ap-la">
                            <!-- <span class="label-title">标签：</span> <span
                                class="label-content">java</span>&nbsp;&nbsp;
                            <span class="label-content">HashMap</span> -->
                        </div>
                        <div class="article-ap-ra">
                            <span><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${articleObj.updateDate.time}" /></span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><i class="fa fa-eye"></i>&nbsp;${articleObj.scanTimes}</span>
                        </div>
                        <div class="cl"></div>
                    </div>
                    <p class="line"></p>
                    <div class="article-content">
                        ${articleObj.content}
                    </div>
                </div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>
</body>
<script type="text/javascript">
    $(function() {
        SyntaxHighlighter.all();
        
    })
</script>
</html>
