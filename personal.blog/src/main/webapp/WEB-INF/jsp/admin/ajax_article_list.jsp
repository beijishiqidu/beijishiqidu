<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pagination" uri="/WEB-INF/pagination.tld"%>

<style>
.customer-info-list table thead th.title {
	width: 440px;
}

.customer-info-list table thead th.date_time {
    width: 220px;
}

.customer-info-list table thead th.type {
    width: 120px;
}

.customer-info-list table thead th.scan_times {
    width: 100px;
}

.customer-info-list table thead th.operation {
	width: 100px;
}
.customer-info-list
{
	text-align: left;
}

</style>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page" />
<div class="customer-info-list">
	<h3>文章列表</h3>
	<table width="100%" height="100%">
		<thead>
			<th class="title">文章标题</th>
            <th class="date_time">创建时间</th>
            <th class="date_time">更新时间</th>
            <th class="type">文章类型</th>
            <th class="scan_times">浏览次数</th>
			<th class="operation">操作</th>
		</thead>
		<tbody>
			<c:forEach items="${pagination.items}" var="list">
				<tr>
					<td title="${list.title }"><div>
							<span <%-- class="pointer"
								onclick="Event.forwardNewsDetailPageInNewPage(${list.id});" --%>>${list.title }</span>
						</div></td>
                    <td>
                        <div><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${list.createDate.time}" /></div>
                    </td>
                    <td>
                        <div><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${list.updateDate.time}" /></div>
                    </td>
                    <td>
                        <div>${list.type.typeName}</div>
                    </td>
                    <td>
                        <div>${list.scanTimes}</div>
                    </td>
					<td><a href="javascript:Event.forwardBackendArticleEditPage(${list.id})">编辑</a>&nbsp;
						<a href="javascript:App.deleteArticleById(${list.id},${(pagination.currentIndex)*pagination.pageSize},${pagination.pageSize})">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="99"><pagination:conf
						action="${path}/admin/article/refreshAdminArticleList.html"
						pageUtil="${pagination}" targetId="main-content-wrapper" /></td>
			</tr>
		</tfoot>
	</table>
</div>


