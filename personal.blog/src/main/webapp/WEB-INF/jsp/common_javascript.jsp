<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page" />
<script type="text/javascript" src="${path}/javascript/lib/01-jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${path}/javascript/lib/04-jquery.placeholder.min.js"></script>
<script type="text/javascript" src="${path}/javascript/lib/03-jquery-ui.min.js"></script>
<script type="text/javascript" src="${path}/javascript/lib/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${path}/javascript/lib/jquery.colorbox-min.js"></script>
<script type="text/javascript" src="${path}/javascript/app/common.js"></script>
<script type="text/javascript" src="${path}/javascript/app/event.js"></script>
<script type="text/javascript" src="${path}/javascript/app/app.js"></script>
<script type="text/javascript">
    $(function() {
        App.setUrlPath("${path}");
    })
</script>

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