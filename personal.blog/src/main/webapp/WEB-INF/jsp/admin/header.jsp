<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${path}/style/backend-article.css" type="text/css" />
<div class="b-header-container-wrapper">
    <div class="header-container">
        <div class="navigate-container">
            <div class="left-area">
                <ul>
                    <li class="first" url="${path}"><span>北极十七度主页</span></li>
                    <li class="middle" url="${path}/type"><span>分类</span></li>
                    <li class="last" url="${path}/message"><span>留言</span></li>
                    <li class="last" url="${path}/photos"><span>相册</span></li>
                    <li class="last" url="${path}/notes"><span>随笔</span></li>
                    <li class="last" url="${path}/about"><span>关于我</span></li>
                    <div class="cl"></div>
                </ul>
            </div>
            <div class="right-area">纪念逝去的青春</div>
            <div class="cl"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function(){
	$('.b-header-container-wrapper .left-area li').each(function(){
		$(this).off('click').on('click', function(){
			location.href=$(this).attr('url');
		});
	});
});
</script>