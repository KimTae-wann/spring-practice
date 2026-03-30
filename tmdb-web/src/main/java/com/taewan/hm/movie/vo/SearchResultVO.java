package com.taewan.hm.movie.vo;

import java.util.List;

public class SearchResultVO {

	private List<MovieVO> result;
	private int count;
	
	public List<MovieVO> getResult() {
		return this.result;
	}
	
	public void setResult(List<MovieVO> result) {
		this.result = result;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
