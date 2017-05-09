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
        <div class="about-content-container">
            <table class="photo">
                <tr>
                    <td><img src="${path}/images/100.png" /></td>
                </tr>
            </table>
            <div class="self-note">
                <p class="content">我常常想，我可以写多久的代码六七十岁怎么办，后来我想通了，怕什么，那会我仍然可以写</p>
                <p class="sign">--北极十七度</p>
                <p class="red-line"></p>
            </div>
            <div class="intro-container">
                <div class="child-div">
                    <p class="v-line title">基本信息</p>
                    <p class="v-line">英文名： Jack</p>
                    <p class="v-line">血型： B型</p>
                    <p class="v-line">星座： 狮子座</p>
                    <p class="v-line">所在地： 陕西省西安市</p>
                    
                </div>
                <div class="child-div">
                    <p class="v-line title">联系方式</p>
                    <p class="v-line">手机号： 保密</p>
                    <p class="v-line">邮箱： 396212976@qq.com</p>
                    <p class="v-line">QQ：396212976</p>
                    <p class="v-line">微信： aiqingmugua2013</p>
                </div>
                <div class="child-div">
                    <p class="v-line title">关于我</p>
                    <p class="v-line" style="line-height: 24px">没有在深夜痛哭做的人，不足以道人生我常常想，我可以写多久的代码六七十岁怎么办，后来我想通了怕什么，那会我仍然可以写，我本将心向明月，奈何明月照沟渠。</p>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>
</body>
</html>
