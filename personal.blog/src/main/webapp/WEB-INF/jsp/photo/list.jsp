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
                            <c:forEach items="${photoTypeCount}" var="type">
                                <li onclick="Event.openInCurrentTab('/photo/list/${type.typeId}')" <c:if test="${type.typeId==id}">class="article-hover"</c:if>><c:out value="${type.typeName}"></c:out>(<c:out value="${type.typeCount}"></c:out>)</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="p-right-area">
            
                <c:forEach items="${photoList}" var="list" step="1" varStatus="status">
                    <c:choose>
                        <c:when test="${status.count<listSize or listSize%2==0}">
                            <c:if test="${status.count%2!=0}">
                                <div class="line-item">
                                    <div class="rl">
                                        <div class="img-p">
                                            <img src="${list.urlPath}"/>
                                        </div>
                                        <div class="opacity-lay"><p>${list.album.title}</p></div>
                                    </div>
                                    <div class="rm">
                                        <div class="triangle-right fl"></div>
                                        <div class="navigate-v-line">
                                            <div class="v-line height-bottom"></div>
                                            <p ><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                                            
                                            <div class="v-line height-top"></div>
                                            <p class="bottom"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                                            <div class="v-line height-middle"></div>
                                            
                                        </div>
                                        <div class="triangle-left fr"></div>
                                    </div>
                            </c:if>
                            <c:if test="${status.count%2==0}">
                                <div class="rr">
                                    <div class="img-p"><img src="${list.urlPath}"/></div>
                                    <div class="opacity-lay"><p>${list.album.title}</p>
                                    </div>
                                </div>
                                <div class="cl"></div>
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="line-item">
                                <div class="rl">
                                    <div class="img-p"><img src="${list.urlPath}"/></div>
                                    <div class="opacity-lay"><p>${list.album.title}</p></div>
                                </div>
                                <div class="rm">
                                    <div class="triangle-right fl"></div>
                                    <div class="navigate-v-line">
                                        <div class="v-line height-bottom"></div>
                                        <p ><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                                    </div>
                                </div>
                                <div class="cl"></div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${status.count%2==0 and listSize!=status.count}">
                        <div class="v-line height-bottom"></div>
                    </c:if>
                </c:forEach>
                
                <%-- 
            
            
                <c:forEach items="${photoList}" var="list" step="1" varStatus="status">
                    <c:if test="${status.count%2!=0}">
                        <div class="line-item">
                        <div class="rl"><img src="${path}/images/p05.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                        <div class="rm">
                            <div class="triangle-right fl"></div>
                            <div class="navigate-v-line">
                                <div class="v-line height-bottom"></div>
                                <p ><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                    </c:if>
                    
                    <c:if test="${status.count%2!=0} && ${status.count<listSize}">
                        <div class="v-line height-top"></div>
                        <p class="bottom"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></p>
                        <div class="v-line height-middle"></div>
                    </c:if>
                    
                    <c:if test="${status.count%2!=0} && ${status.count<listSize}">
                        </div>
                        <div class="triangle-left fr"></div>
                    </c:if>
                    
                    <c:if test="${status.count%2!=0}">
                        </div>
                    </c:if>
                    
                    <c:if test="${status.count%2==0}">
                    </div>
                        <div class="rr"><img src="${path}/images/p06.png"/><div class="opacity-lay"><p>天空之城，永远美丽</p></div></div>
                    </c:if>
                            
                    <c:if test="${status.count%2==0} || ${status.count==listSize}">
                            <div class="cl"></div>
                        </div>
                    </c:if>
                    
                    <c:if test="${status.count<=(listSize-2)}">
                    </div>
                        <div class="v-line height-bottom"></div>
                    </c:if>
                </c:forEach> --%>
            
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
