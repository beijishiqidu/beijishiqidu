<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page" />

<c:set value="${releaseVersion }" var="version" scope="request"/>
<c:choose>
    <c:when test="${localEnv }">
        <c:forEach items="${jsLibFiles}" var="file">
            <script type="text/javascript" src="${path}/javascript/lib/${file.name}?version=${version}"></script>
        </c:forEach>
        <c:forEach items="${jsAppFiles}" var="file">
            <script type="text/javascript" src="${path}/javascript/app/${file.name}?version=${version}"></script>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <script type="text/javascript" src="${path}/compressed/lib-min.js?version=${version}"></script>
        <script type="text/javascript" src="${path}/compressed/app-min.js?version=${version}"></script>
    </c:otherwise>
</c:choose>

<script type="text/javascript" src="${path}/third-party/SyntaxHighlighter/shCore.js?version=${version}"></script>

<script type="text/javascript">
    $(function() {
        App.setUrlPath("${path}");
    })
</script>