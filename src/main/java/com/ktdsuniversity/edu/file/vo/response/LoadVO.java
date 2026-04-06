package com.ktdsuniversity.edu.file.vo.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

public class LoadVO {
	private String displayName;
	private String extendName;
	private long fileLength;
	private String filePath;

	private File file;

	private Resource resource;

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		
		try {
			this.displayName = URLEncoder.encode(displayName, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
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
		this.file = new File(this.filePath);
		try {
			FileInputStream fileStream = new FileInputStream(this.file);
			this.resource = new InputStreamResource(fileStream);
		} catch (FileNotFoundException fnfe) {
		}
	}

	public File getFile() {
		return this.file;
	}

	public Resource getResource() {
		return this.resource;
	}
}
