<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>register movie</title>
    <link rel="stylesheet" type="text/css" href="/css/tmdb-write.css" />
  </head>
  <body>
    <h1>영화 등록 페이지</h1>
    <form method="post" action="/write" enctype="multipart/form-data">
      <div class="grid write">
        <label for="posterImgFile">포스터 URL</label>
        <div id="poster-image" class="poster-image">
          <input type="file" name="posterImgFile"/>
        </div>

        <label for="title">제목</label>
        <input
          type="text"
          id="title"
          name="title"
          placeholder="제목을 입력하세요."
        />

        <label for="movieRating">평점</label>
        <input
          type="text"
          id="movieRating"
          name="movieRating"
          placeholder="평점을 입력하세요."
        />

        <label for="openCountry">개봉국가</label>
        <input
          type="text"
          id="openCountry"
          name="openCountry"
          placeholder="개봉국가를 입력하세요."
        />

        <label for="runningTime">상영시간</label>
        <input
          type="text"
          id="runningTime"
          name="runningTime"
          placeholder="상영시간을 입력하세요."
        />

        <label for="introduce">개요</label>
        <input
          type="text"
          id="introduce"
          name="introduce"
          placeholder="개요를 입력하세요."
        />

        <label for="synopsis">줄거리</label>
        <textarea
          id="synopsis"
          name="synopsis"
          placeholder="줄거리를 입력하세요."
        >
        </textarea>

        <label for="originalTitle">원제</label>
        <input
          type="text"
          id="originalTitle"
          name="originalTitle"
          placeholder="원제를 입력하세요."
        />

        <label for="state">상태</label>
        <input
          type="text"
          id="state"
          name="state"
          placeholder="상태를 입력하세요."
        />

        <label for="language">언어</label>
        <input
          type="text"
          id="language"
          name="language"
          placeholder="언어를 입력하세요."
        />

        <label for="budget">예산</label>
        <input
          type="text"
          id="budget"
          name="budget"
          placeholder="예산을 입력하세요."
        />

        <label for="profit">수익</label>
        <input
          type="text"
          id="profit"
          name="profit"
          placeholder="수익을 입력하세요."
        />

        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </form>
  </body>
</html>
