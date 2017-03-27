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
                    <div class="rl"><img src="${path}/images/p01.png"/></div>
                    <div class="rm">
                        <div class="top-area v-line">
                        </div>
                        <div class="bottom-area v-line">
                        </div>
                    </div>
                    <div class="rr"><img src="${path}/images/p02.png"/></div>
                    <div class="cl"></div>
                </div>
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p03.png"/></div>
                    <div class="rm"></div>
                    <div class="rr"><img src="${path}/images/p04.png"/></div>
                    <div class="cl"></div>
                </div>
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p05.png"/></div>
                    <div class="rm"></div>
                    <div class="rr"><img src="${path}/images/p06.png"/></div>
                    <div class="cl"></div>
                </div>
                <div class="line-item">
                    <div class="rl"><img src="${path}/images/p07.png"/></div>
                    <div class="rm"></div>
                    <div class="rr"><img src="${path}/images/p01.png"/></div>
                    <div class="cl"></div>
                </div>
            </div>
            <div class="cl"></div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>
</body>
</html>
