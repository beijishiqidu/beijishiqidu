<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path"
	scope="page" />
<%@include file="../common_javascript.jsp"%>
<div id="top_bg">
	<div class="top">
		<!--导航开始-->
		<div class="nav_z">
			<ul id="navul" class="cl">
				<li class="sub-navul"><a href="#">文章管理</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/article/list');">文章列表</span></li>
						<li><span onclick="Event.openInCurrentTab('/admin/article/add');">新增文章</span></li>
					</ul></li>
				<li class="sub-navul"><a href="#">分类管理</a>
					<ul>
                        <li><span onclick="Event.openInCurrentTab('/admin/type/list/article');">文章分类列表</span></li>
                        <li><span onclick="Event.openInCurrentTab('/admin/type/list/photo-type');">相册分类列表</span></li>
                        <li><span onclick="Event.openInCurrentTab('/admin/type/add/article-type');">增加文章类型</span></li>
                        <li><span onclick="Event.openInCurrentTab('/admin/type/add/photo-type');">增加相册类型</span></li>
					</ul></li>
				<li class="sub-navul"><a href="#">相册管理</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/photo/manage/list');">相册列表</span></li>
						<li><span onclick="Event.openInCurrentTab('/admin/photo/add');">新增相册</span></li>
					</ul></li>
				<li class="sub-navul"><a href="#">系统管理</a>
                    <ul>
                        <li><span onclick="Event.openInCurrentTab('/admin/changePassword.html');">修改密码(待实现)</span></li>
                    </ul>
                </li>
			</ul>
			<span class="logout-area">你好，北极十七度，<span class="logout" onclick="Event.openInCurrentTab('/loginout.do')">退出</span></span>
		</div>
		<!--导航结束-->
		<script type="text/javascript">
            $(".navbg").capacityFixed();
        </script>
	</div>
</div>
