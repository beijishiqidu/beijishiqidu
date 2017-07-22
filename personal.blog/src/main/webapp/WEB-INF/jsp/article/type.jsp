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
    <%@include file="../header.jsp"%>
    <div class="body-container">
        <div class="content-container">
            <div class="left-area">
                <div class="article-area">
                    <div class="title">
                        <i class="fa fa-list"></i>&nbsp;<span>文章列表</span>
                    </div>
                    <div class="content">
                        <ul>
                            <c:forEach items="${articleTypeCount}" var="type">
                                <li onclick="Event.openInCurrentTab('/article/type/${type.typeId}')" <c:if test="${type.typeId==id}">class="article-hover"</c:if>><c:out value="${type.typeName}"></c:out>(<c:out value="${type.typeCount}"></c:out>)</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="right-area">
                <c:forEach items="${pagination.items}" var="item">
                    <div class="article-item">
                        <p class="title"><a href="${path}/article/detail/${item.id}">${item.title}</a></p>
                        <div class="ap-area">
                            <div class="article-ap-la">
                                <span class="label-title">标签：</span> <span
                                    class="label-content">java</span>&nbsp;&nbsp;
                                <span class="label-content">HashMap</span>
                            </div>
                            <div class="article-ap-ra">
                                <span><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${item.updateDate.time}" /></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                <span><i class="fa fa-eye"></i>&nbsp;${item.scanTimes}</span>
                            </div>
                            <div class="cl"></div>
                        </div>
                        <p class="line"></p>
                        <div class="article-content">
                            <p>${item.contentSummary}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="cl"></div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>
</body>
</html>
