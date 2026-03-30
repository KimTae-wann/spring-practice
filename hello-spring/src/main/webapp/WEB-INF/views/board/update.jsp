<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 수정</title>
    <link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
  </head>
  <body>
    <h1>게시글 수정</h1>
    <!-- action -> form 내부의 value를 전송할 EndPoint -->
    <form method="post" action="/update/${article.id}">
      <div class="grid write">
        <label for="subject">제목</label>
        <input
          type="text"
          id="subject"
          name="subject"
          placeholder="제목을 입력하세요."
          value="${article.subject }"
        />

        <label for="email">이메일</label>
        <input
          type="email"
          id="email"
          name="email"
          placeholder="이메일을 입력하세요."
          value="${article.email }"
        />

        <label for="content">내용</label>
        <textarea id="content" name="content" placeholder="내용을 입력하세요.">
        ${article.content}
        </textarea>

        <!-- <span>내용</span>
        <pre>${article.content}</pre> -->

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
