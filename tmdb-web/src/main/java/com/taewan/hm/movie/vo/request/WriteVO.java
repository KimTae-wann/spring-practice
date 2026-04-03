package com.taewan.hm.movie.vo.request;

import org.springframework.web.multipart.MultipartFile;

import com.taewan.hm.movie.vo.MovieVO;

public class WriteVO extends MovieVO{
	
	private MultipartFile posterImgFile;
	
	public MultipartFile getPosterImgFile() {
		return this.posterImgFile;
	}
	
	public void setPosterImgFile(MultipartFile posterImgFile) {
		this.posterImgFile = posterImgFile;
	}
}
