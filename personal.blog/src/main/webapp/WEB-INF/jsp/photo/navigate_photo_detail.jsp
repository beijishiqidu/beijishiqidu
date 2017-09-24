<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="photo-navigate-body-wapper">
    <div class="left-navigate">
        <table style="width: 100%; height: 100%;">
          <tr>
             <td style="text-align: center;">
                 <c:if test="${hasPre}">
                    <i class="fa fa-chevron-left fa-5x" onclick="App.prevPhoto()"></i>
                 </c:if>
             </td>
          </tr>
        </table>
    </div>
    <div class="center-content" data-first-result="${firstResult}" data-album-id="${albumId }">
        <img src="${imgPath}"/>
    </div>
    <div class="right-navigate">
        <table style="width: 100%; height: 100%;">
          <tr>
             <td style="text-align: center;">
                  <c:if test="${hasNext}">
                    <i class="fa fa-chevron-right fa-5x" onclick="App.nextPhoto()"></i>
                  </c:if>
             </td>
          </tr>
        </table>
    </div>
</div>
