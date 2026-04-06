<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TMDB</title>
<link type="text/css" rel="stylesheet" href="/css/common.css">
<link type="text/css" rel="stylesheet" href="/css/movie/list.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <div class="content">
        <h2 class="content__title">인기 영화</h2>
        <div class="content__body">
            <div class="body__side">
                <div>
                    <div>정렬</div>
                    <div>></div>
                </div>
                <div>
                    <div>볼 수 있는 곳</div>
                    <div>
                        <div>37</div>
                        <div>></div>
                    </div>
                </div>
                <div>
                    <div>필터</div>
                    <div>></div>
                </div>
                <div class="body__side--search">검색</div>
            </div>
            <div class="body__content">
                <c:forEach items="${movieList}" var="movie">
                    <a href="/view/${movie.movieId}">
                        <div>
                            <div>
                                <img src="/file/${movie.movieId}" alt="${movie.posterUrl}">
                            </div>
                            <div>
                                <div>${movie.title}</div>
                                <c:if test="${not empty movie.openDate}">
                                    <div>${movie.openDate}, ${movie.openYear}</div>
                                </c:if>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="footer"></div>
</body>
</html>