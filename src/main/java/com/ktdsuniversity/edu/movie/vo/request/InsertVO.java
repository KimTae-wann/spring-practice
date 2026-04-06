package com.ktdsuniversity.edu.movie.vo.request;

import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.movie.vo.MovieVO;

public class InsertVO extends MovieVO {
	private MultipartFile file;

	public MultipartFile getFile() {
		return this.file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
