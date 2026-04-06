package com.taewan.hm.files.vo.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

public class LoadPosterVO {

	private String displayName;
	private String extendName;
	private long fileLength;
	private String filePath;
	
	// 사용자에게 전달해줄 파일 객체
	private File file;
	
	// 브라우저에게 전달하기 위한 파일 객체
	// 데이터를 읽어올 수 있는 통로
	private Resource resource;

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		// JAVA 기반 애플리케이션에서 파일을 다운로드 할 때
		// 영어를 제외한 글자들이 사라지는 현상.
		// --> 사라지지 않도록 다국어 지원
		
		this.displayName = displayName;
		try {
			this.displayName = URLEncoder.encode(displayName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// 거의 발상하지 않는 예외인데, "UTF-8"을 적어주면 예외 발생 거의 하지 않음
		}
	}

	public String getExtendName() {
		return this.extendName;
	}

	public void setExtendName(String extendName) {
		this.extendName = extendName;
	}

	public long getFileLength() {
		return this.fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		
		// this.file 생성
		this.file = new File(this.filePath);
		// this.resource 생성
		// 파일의 용량이 크기 때문에 파이프형식으로 처리
		try {
			FileInputStream fileStream = new FileInputStream(this.file);
			this.resource = new InputStreamResource(fileStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
		
	}

	public File getFile() {
		return this.file;
	}

	public Resource getResource() {
		return this.resource;
	}
}
