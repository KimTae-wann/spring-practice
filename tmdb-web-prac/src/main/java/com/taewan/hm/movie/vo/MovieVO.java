package com.taewan.hm.movie.vo;

public class MovieVO {

	private String movieId;
	private String posterUrl;
	private String title;
	private String movieRating;
	private String openDate;
	private String openCountry;
	private int runningTime;
	private String introduce;
	private String synopsis;
	private String originalTitle;
	private String state;
	private String language;
	private int budget;
	private int profit;
	public String getMovieId() {
		if (this.movieId != null) {
			this.movieId = this.movieId.replace("<", "&lt").replace(">", "&gt");
					
		}
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getPosterUrl() {
		if (this.posterUrl != null) {
			this.posterUrl = this.posterUrl.replace("<", "&lt").replace(">", "&gt");
					
		}
		return this.posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	public String getTitle() {
		if (this.title != null) {
			this.title = this.title.replace("<", "&lt").replace(">", "&gt");
					
		}
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMovieRating() {
		if (this.movieRating != null) {
			this.movieRating = this.movieRating.replace("<", "&lt").replace(">", "&gt");
		}
		return this.movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}
	public String getOpenDate() {
		if (this.openDate != null) {
			this.openDate = this.openDate.replace("<", "&lt").replace(">", "&gt");
		}
		return this.openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getOpenCountry() {
		if (this.openCountry != null) {
			this.openCountry = this.openCountry.replace("<", "&lt").replace(">", "&gt");
		}
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
		if (this.introduce != null) {
			this.introduce = this.introduce.replace("<", "&lt").replace(">", "&gt");
		}
		return this.introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getSynopsis() {
		if (this.synopsis != null) {
			this.synopsis = this.synopsis.replace("<", "&lt").replace(">", "&gt");
		}
		return this.synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getOriginalTitle() {
		if (this.originalTitle != null) {
			this.originalTitle = this.originalTitle.replace("<", "&lt").replace(">", "&gt");
		}
		return this.originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public String getState() {
		if (this.state != null) {
			this.state = this.state.replace("<", "&lt").replace(">", "&gt");
		}
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLanguage() {
		if (this.language != null) {
			this.language = this.language.replace("<", "&lt").replace(">", "&gt");
		}
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
	
	
	
}
