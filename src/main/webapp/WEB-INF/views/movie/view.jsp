<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${movie.title} (${movie.openYear}) - The Movie Database (TMDB)</title>
<link type="text/css" rel="stylesheet" href="/css/common.css">
<link type="text/css" rel="stylesheet" href="/css/movie/view.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <div class="header__sub">
        <div>개요</div>
        <div>미디어</div>
        <div>팬덤</div>
        <div>공유</div>
    </div>
    <div class="content" style="background-image: linear-gradient(rgba(0,0,0,0.8), rgba(0,0,0,0.8)), 
                      url(/file/${movie.movieId});">
        <div class="content__header">
            <div class="content__header__left">
                <img src="/file/${movie.movieId}" alt="${movie.posterUrl}">
            </div>
            <div class="content__header__right">
                <div>
                    <div>
                        <div class="movie__title">${movie.title}</div>
                        <div class="movie__openyear">(${movie.openYear})</div>
                    </div>
                    <div>
                        <div class="movie__rate">${movie.movieRating}</div>
                        <div class="movie__opendate">${movie.openDate}
                            <span>(${movie.openCountry})</span>
                        </div>
                        <span>·</span>
                        <div class="movie__genre">
                            <c:choose>
                                <c:when test="${not empty categoryNameList}">
                                    <c:forEach items="${categoryNameList}" var="categoryName">
                                        ${categoryName}
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div>장르 미정</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <span>·</span>
                        <div class="movie__runningtime">${movie.runningTime}</div>
                    </div>
                </div>
                <div>
                    <div>
                        <div class="movie__score">회원 점수</div>
                        <div class="movie__yourvibe">당신의 바이브는 어떤가요?</div>
                    </div>
                    <div>
                        <div>
                        </div>
                        <div class="movie__trailer">트레일러 재생</div>
                    </div>
                </div>
                <div>
                    <div class="movie__introduce">
                            ${movie.introduce}
                    </div>
                    <div class="synopsis">개요</div>
                    <div class="movie__synopsis">${movie.synopsis}</div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"></div>
</body>
</html>