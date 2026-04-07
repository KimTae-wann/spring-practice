<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
    <jsp:param value="회원 목록" name="title"/>
</jsp:include>
	  <div class="grid list">
	    <h1>회원 목록</h1>
	    
	    <div>총 ${searchCount }명의 회원이 검색되었습니다.</div>
	
	    <table class="grid">
	        <colgroup>
	            <col width="*"/>
	            <col width="300"/>
	            <col width="300"/>
	        </colgroup>
	        <thead>
	            <tr>
		            <th>이메일</th>
		            <th>이름</th>
		            <th>비밀번호</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:choose>
			        <c:when test="${not empty searchResult}">
			            <c:forEach items="${searchResult}" var="member">
			                <tr>
			                  <td>${member.email }</td>
			                  <td>${member.name }</td>
			                  <td>${member.password }</td>
			                </tr>
			            </c:forEach>
			        </c:when>
			        <c:otherwise>
			            <td colspan="3">등록된 회원이 없습니다</td>
			        </c:otherwise>
			    </c:choose>
	        </tbody>
	    </table>
	    <div class="btn-group">
	        <div class="right-align">
	            <a href="/regist">새로운 회원 등록</a>
	        </div>
	    </div>
    </div>

  </body>
</html>
