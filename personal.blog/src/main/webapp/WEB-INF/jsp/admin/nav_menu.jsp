<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path"
	scope="page" />
	
<script type="text/javascript" src="${path}/javascript/app/top-menu.js"></script>
<script type="text/javascript">
    $(function() {
        App.setUrlPath("${path}");
    })
</script>

<div id="top_bg">
	<div class="top">
		<!--导航开始-->
		<div class="nav_z">
			<ul id="navul" class="cl">
				<li><a href="#">文章管理</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/article/list.html');">文章列表</span></li>
						<li><span onclick="Event.openInCurrentTab('/admin/article/add.html');">新增文章</span></li>
					</ul></li>
				<li><a href="#">随笔管理</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/customerInfoList.html');">随笔列表</span></li>
						<li><span onclick="Event.openInCurrentTab('/admin/customerJoinList.html');">新增随笔</span></li>
					</ul></li>
				<li><a href="#">分类管理</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/addDesigner.html');">新增文章分类</span></li>
						<li><span onclick="Event.openInCurrentTab('/admin/designerList.html');">新增相册分类</span></li>
                        <li><span onclick="Event.openInCurrentTab('/admin/designerList.html');">分类列表</span></li>
					</ul></li>
				<li><a href="#">相册管理</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/designCaseList.html');">相册列表</span></li>
						<li><span onclick="Event.openInCurrentTab('/admin/addDesignCase.html');">新增相册</span></li>
					</ul></li>
				<li><a href="#">其他</a>
					<ul>
						<li><span onclick="Event.openInCurrentTab('/admin/contactUs.html');">关于我</span></li>
					</ul></li>
				<li><a href="#">系统管理</a>
                    <ul>
                        <li><span onclick="Event.openInCurrentTab('/admin/changePassword.html');">修改密码</span></li>
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
