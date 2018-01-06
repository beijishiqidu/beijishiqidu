<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<%@include file="common_meta_link.jsp"%>
<%@include file="common_style.jsp"%>
<%@include file="common_javascript.jsp"%>
<title>北极十七度的个人主页</title>
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="body-container">
        <div class="content-container">
            <div class="left-area">
                <div class="personal-info">
                    <div class="photo">
                        <img alt="个人图像" src="${path}/images/100.png">
                    </div>
                    <div class="name">北极十七度</div>
                    <div class="address">陕西&nbsp;&nbsp;西安</div>
                    <div class="line"></div>
                    <p class="mark">我常常想，我可以写多久的代码六七十岁怎么办，后来我想通了怕什么，那会我仍然可以写</p>
                </div>
                <div class="article-area">
                    <div class="title">
                        <i class="fa fa-list"></i>&nbsp;<span>文章列表</span>
                    </div>
                    <div class="content">
                        <ul>
                            <c:forEach items="${articleTypeCount}" var="type">
                                <%-- <li><c:out value="${type.typeName}"></c:out>(<c:out value="${type.typeCount}"></c:out>)</li> --%>
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
                                <!-- <span class="label-title">标签：</span> <span
                                    class="label-content">java</span>&nbsp;&nbsp;
                                <span class="label-content">HashMap</span> -->
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
    <%@include file="footer.jsp"%>
</body>
</html>
