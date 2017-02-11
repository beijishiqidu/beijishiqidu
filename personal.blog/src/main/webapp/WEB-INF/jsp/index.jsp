<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="common_meta_link.jsp"%>
<%@include file="common_style.jsp"%>
<%@include file="common_javascript.jsp"%>
<title>北极十七度的个人主页</title>
</head>
<body>
    <div class="header-container-wrapper">
        <div class="header-container">
            <div class="navigate-container">
                <div class="left-area">
                    <ul>
                        <li class="first"><span>北极十七度主页</span></li>
                        <li class="middle"><span>分类</span></li>
                        <li class="last"><span>留言</span></li>
                        <li class="last"><span>相册</span></li>
                        <li class="last"><span>随笔</span></li>
                        <li class="last"><span>关于我</span></li>
                        <div class="cl"></div>
                    </ul>
                </div>
                <div class="right-area">纪念逝去的青春</div>
                <div class="cl"></div>
            </div>
        </div>
    </div>
    <!-- style="background: url(${path}/${backGroundImgPath}) no-repeat;width:100%;background-size:100% 100%" -->
    <div class="body-container">
        <div class="content-container">
            <div class="left-area">
                <div class="personal-info">
                    <div class="photo">
                        <img alt="个人图像" src="${path}/images/100.png">
                    </div>
                    <div class="name">北极十七度</div>
                    <div class="address">陕西&nbsp;&nbsp;西安</div>
                    <div class="line"></div>
                    <p class="mark">我常常想，我可以写多久的代码六七十岁怎么办，后来我想通了怕什么，那会我仍然可以写</p>
                </div>
                <div class="article-area">
                    <div class="title">
                        <i class="fa fa-list"></i>&nbsp;<span>文章列表</span>
                    </div>
                    <div class="content">
                        <ul>
                            <li>移动开发(25)</li>
                            <li>WEB前端(20)</li>
                            <li>企业架构(12)</li>
                            <li>编程语言(35)</li>
                            <li>开源软件(75)</li>
                            <li>操作系统(145)</li>
                            <li>研发管理(137)</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="right-area">
                <div class="article-item">
                    <p class="title">深入Java集合学习系列：HashMap的实现原理</p>
                    <div class="ap-area">
                        <div class="article-ap-la">
                            <span class="label-title">标签：</span> <span
                                class="label-content">java</span>&nbsp;&nbsp;
                            <span class="label-content">HashMap</span>
                        </div>
                        <div class="article-ap-ra">
                            <span>2016-06-19 18:58</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><i class="fa fa-eye"></i>&nbsp;289</span>
                        </div>
                        <div class="cl"></div>
                    </div>
                    <p class="line"></p>
                    <div class="article-content">
                        <p>HashMap概述：
                            HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许使用null值和null键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
                            2. HashMap的数据结构：
                            在java编程语言中最基本的结构就是两种，一个是数组，另外一个是模拟指针（引用），所有的数据结构都可以用这两个基本结构来构造的HashMap也不例外。HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体从上图中可以看出，HashMap底层就是一个数组结构，数组中的每一项又是一个链表......</p>
                    </div>
                </div>
                <div class="article-item">
                    <p class="title">深入Java集合学习系列：HashMap的实现原理</p>
                    <div class="ap-area">
                        <div class="article-ap-la">
                            <span class="label-title">标签：</span> <span
                                class="label-content">java</span>&nbsp;&nbsp;
                            <span class="label-content">HashMap</span>
                        </div>
                        <div class="article-ap-ra">
                            <span>2016-06-19 18:58</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><i class="fa fa-eye"></i>&nbsp;289</span>
                        </div>
                        <div class="cl"></div>
                    </div>
                    <p class="line"></p>
                    <div class="article-content">
                        <p>HashMap概述：
                            HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许使用null值和null键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
                            2. HashMap的数据结构：
                            在java编程语言中最基本的结构就是两种，一个是数组，另外一个是模拟指针（引用），所有的数据结构都可以用这两个基本结构来构造的HashMap也不例外。HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体从上图中可以看出，HashMap底层就是一个数组结构，数组中的每一项又是一个链表......</p>
                    </div>
                </div>
                <div class="article-item">
                    <p class="title">深入Java集合学习系列：HashMap的实现原理</p>
                    <div class="ap-area">
                        <div class="article-ap-la">
                            <span class="label-title">标签：</span> <span
                                class="label-content">java</span>&nbsp;&nbsp;
                            <span class="label-content">HashMap</span>
                        </div>
                        <div class="article-ap-ra">
                            <span>2016-06-19 18:58</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><i class="fa fa-eye"></i>&nbsp;289</span>
                        </div>
                        <div class="cl"></div>
                    </div>
                    <p class="line"></p>
                    <div class="article-content">
                        <p>HashMap概述：
                            HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许使用null值和null键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
                            2. HashMap的数据结构：
                            在java编程语言中最基本的结构就是两种，一个是数组，另外一个是模拟指针（引用），所有的数据结构都可以用这两个基本结构来构造的HashMap也不例外。HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体从上图中可以看出，HashMap底层就是一个数组结构，数组中的每一项又是一个链表......</p>
                    </div>
                </div>
            </div>
            <div class="cl"></div>
        </div>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>
