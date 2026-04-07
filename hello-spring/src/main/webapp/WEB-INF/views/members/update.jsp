<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
    <jsp:param value="회원수정" name="title"/>
</jsp:include>
    <h1>회원 수정</h1>
    <!-- action -> form 내부의 value를 전송할 EndPoint -->
    <form method="post" action="/member/update/${user.email}">
      <div class="grid write">
        <label for="email">이메일</label>
        <input
          type="text"
          id="email"
          name="email"
          placeholder="이메일을 입력하세요."
          value="${user.email}"
        />

        <label for="name">이름</label>
        <input
          type="text"
          id="name"
          name="name"
          placeholder="이름을 입력하세요."
          value="${user.name}"
        />

        <label for="password">비밀번호</label>
        <input
          type="text"
          id="password"
          name="password"
          placeholder="비밀번호를 입력하세요."
          value="${user.password }"
        />

        <div class="btn-group">
          <div class="right-align">
            <button type="submit">
            수정
            </button>
            
            
          </div>
        </div>
      </div>
    </form>
  </body>
</html>
