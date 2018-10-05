<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="header-container-wrapper">
	<div class="header-container">
		<div class="navigate-container">
			<div class="left-area">
				<ul>
					<li class="first" url="${path}/"><span><a
							href="${path}/" style="color: #ffffff">北极十七度主页</a></span></li>
							
					<li class="middle" url="${path}/article/type"><span><a
							href="${path}/article/type" style="color: #ffffff">分类</a></span></li>
							
					<li class="last" url="${path}/photo/home"><span><a
							href="${path}/photo/home" style="color: #ffffff">相册</a></span></li>
							
					<li class="last" url="${path}/article/type/91"><span><a
							href="${path}/article/type/91" style="color: #ffffff">随笔</a></span></li>
							
					<li class="last" url="${path}/about/me"><span><a
							href="${path}/about/me" style="color: #ffffff">关于我</a></span></li>
							
					<li class="last" url="/wiki115670/index.html"><span><a
							href="/wiki115670/index.html" style="color: #ffffff">万能钥匙</a></span></li>
							
					<div class="cl"></div>
				</ul>
			</div>
			<div class="right-area">纪念逝去的青春</div>
			<div class="cl"></div>
		</div>
	</div>
</div>
<!-- <script type="text/javascript">
$(function(){
	$('.header-container .left-area li').each(function(){
		$(this).off('click').on('click', function(){
			location.href=$(this).attr('url');
		});
	});
});
</script> -->