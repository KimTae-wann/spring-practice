package com.ktdsuniversity.edu.movie.vo.response;

public class MovieListVO {
	private String movieId;
	private String posterUrl;
	private String title;	
	private String openYear;
	private String openDate;

	public String getMovieId() {
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getPosterUrl() {
		return this.posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOpenYear() {
		return this.openYear;
	}
	public void setOpenYear(String openYear) {
		this.openYear = openYear;
	}
	public String getOpenDate() {
		return this.openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
}
