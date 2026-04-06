<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List</title>
<link rel="stylesheet" type="text/css" href="/css/tmdb-web.css"/>
</head>
<body>
    <h1>${searchCount}개의 영화가 검색되었습니다.</h1>
    
    <hr>

    <c:forEach items="${searchResult}" var="movie">
        <div class="moviesInfo">
            <div class="poster"><img src="/file/${movie.movieId}"<%-- "/image/${movie.posterUrl}.webp" --%>/> </div>
            <div class="movieInfo">
	            <div class="movieItem"><strong>영화 ID:</strong> ${movie.movieId}</div>
	            <div class="movieItem"><strong>제목:</strong> ${movie.title}</div>
	            <div class="movieItem"><strong>평점:</strong> ${movie.movieRating}</div>
	            <div class="movieItem"><strong>개봉일:</strong> ${movie.openDate}</div>
	            <div class="movieItem"><strong>개봉국가:</strong> ${movie.openCountry}</div>
	            <div class="movieItem"><strong>상영시간:</strong> ${movie.runningTime}분</div>
	            <div class="movieItem"><strong>줄거리:</strong> ${movie.synopsis}</div>
	            <div class="movieItem"><strong>원제:</strong> ${movie.originalTitle}</div>
	            <div class="movieItem"><strong>상태:</strong> ${movie.state}</div>
	            <div class="movieItem"><strong>언어:</strong> ${movie.language}</div>
	            <div class="movieItem"><strong>예산:</strong> ${movie.budget}</div>
	            <div class="movieItem"><strong>수익:</strong> ${movie.profit}</div>
            </div>
        </div>
    </c:forEach>
    <div class="btn-group">
        <div class="right-align">
            <a href="/write">영화 등록</a>
        </div>
    </div>
</body>
</html>