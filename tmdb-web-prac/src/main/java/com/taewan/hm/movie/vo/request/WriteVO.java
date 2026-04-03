package com.taewan.hm.movie.vo.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class WriteVO {
	
	@NotBlank(message="movieId 적어")
	private String movieId;
	@NotBlank(message="posterUrl 적어")
	private String posterUrl;
	@NotBlank(message="title 적어")
	private String title;
	private String movieRating;
	private String openDate;
	private String openCountry;
	private int runningTime;
	private String introduce;
	@NotBlank(message="synopsis 적어")
	private String synopsis;
	private String originalTitle;
	@NotBlank(message="state 적어")
	private String state;
	@NotBlank(message="language 적어")
	private String language;
	private int budget;
	private int profit;
	
	private MultipartFile posterImgFile;
	
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
	public String getMovieRating() {
		return this.movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}
	public String getOpenDate() {
		return this.openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getOpenCountry() {
		return this.openCountry;
	}
	public void setOpenCountry(String openCountry) {
		this.openCountry = openCountry;
	}
	public int getRunningTime() {
		return this.runningTime;
	}
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	public String getIntroduce() {
		return this.introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getSynopsis() {
		return this.synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getOriginalTitle() {
		return this.originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getBudget() {
		return this.budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public int getProfit() {
		return this.profit;
	}
	public void setProfit(int profit) {
		this.profit = profit;
	}
	
	public MultipartFile getPosterImgFile() {
		return this.posterImgFile;
	}
	
	public void setPosterImgFile(MultipartFile posterImgFile) {
		this.posterImgFile = posterImgFile;
	}
}
