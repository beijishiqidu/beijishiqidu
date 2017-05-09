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
                        <i class="fa fa-list"></i>&nbsp;<span>相册分类</span>
                    </div>
                    <div class="content">
                        <ul>
                            <li>个人写真(25)</li>
                            <li>生活照(20)</li>
                            <li>工作照(12)</li>
                            <li>游玩照(35)</li>
                            <li>在他乡(75)</li>
                            <li>所见(145)</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="p-right-area">
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p01.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="rm">
                        <div class="triangle-right fl"></div>
                        <div class="navigate-v-line">
                            <p class="top"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                            <div class="v-line height-top"></div>
                            <p class="bottom"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                            <div class="v-line height-middle"></div>
                        </div>
                        <div class="triangle-left fr"></div>
                    </div>
                    <div class="rr"><img src="${path}/images/p02.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="cl"></div>
                </div>
                <div class="v-line height-bottom"></div>
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p03.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="rm">
                        <div class="triangle-right fl"></div>
                        <div class="navigate-v-line">
                            <div class="v-line height-bottom"></div>
                            <p class="top"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                            <div class="v-line height-top"></div>
                            <p class="bottom"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                            <div class="v-line height-middle"></div>
                        </div>
                        <div class="triangle-left fr"></div>
                    </div>
                    <div class="rr"><img src="${path}/images/p04.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="cl"></div>
                </div>
                <div class="v-line height-bottom"></div>
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p05.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="rm">
                        <div class="triangle-right fl"></div>
                        <div class="navigate-v-line">
                            <div class="v-line height-bottom"></div>
                            <p class="top"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                            <div class="v-line height-top"></div>
                            <p class="bottom"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                            <div class="v-line height-middle"></div>
                        </div>
                        <div class="triangle-left fr"></div>
                    </div>
                    <div class="rr"><img src="${path}/images/p06.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="cl"></div>
                </div>
                <div class="v-line height-bottom"></div>
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p07.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    <div class="rm">
                        <div class="triangle-right fl"></div>
                        <div class="navigate-v-line">
                            <div class="v-line height-bottom"></div>
                            <p class="top"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                        </div>
                    </div>
                    <div class="cl"></div>
                </div>
            </div>
            <div class="cl"></div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>
    
    <script type="text/javascript">
    $(function(){
        $('.p-right-area .line-item .rl, .p-right-area .line-item .rr').each(function(){
            $(this).mouseenter(function(){
            	$(this).find('.opacity-lay').show();
            }).mouseleave(function(){
            	$(this).find('.opacity-lay').hide();
            });
        });
    });
    </script>
</body>
</html>
