package com.ktdsuniversity.edu.members.vo;

import java.util.List;

public class SearchResultVO {

	private List<MembersVO> result;
	private int count;
	
	public List<MembersVO> getResult() {
		return result;
	}
	
	public void setResult(List<MembersVO> result) {
		this.result = result;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	
}
