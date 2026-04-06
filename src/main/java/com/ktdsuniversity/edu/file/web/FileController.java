package com.ktdsuniversity.edu.file.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ktdsuniversity.edu.file.service.FileService;
import com.ktdsuniversity.edu.file.vo.response.LoadVO;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	private Map<String, String> mimeTypeMap;

	public FileController() {
		this.mimeTypeMap = new HashMap<>();
		this.mimeTypeMap.put("txt", "text/plain");
		this.mimeTypeMap.put("jpg", "image/jpg");
		this.mimeTypeMap.put("gif", "image/gif");
		this.mimeTypeMap.put("jpeg", "image/jpeg");
		this.mimeTypeMap.put("png", "image/png");
		this.mimeTypeMap.put("webp", "image/webp");

		this.mimeTypeMap.put("css", "text/css");
		this.mimeTypeMap.put("html", "text/html");
		this.mimeTypeMap.put("js", "text/javascript");
		this.mimeTypeMap.put("csv", "text/csv");

		this.mimeTypeMap.put("xls", "application/vnd.ms-excel");
		this.mimeTypeMap.put("doc", "application/msword");
		this.mimeTypeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		this.mimeTypeMap.put("ppt", "application/vnd.ms-powerpoint");

	}

	@GetMapping("/file/{movieId}")
	public ResponseEntity<Resource> doLoadFile(@PathVariable String movieId) {
		LoadVO loadVO = this.fileService.readFileByMovieId(movieId);

		if (loadVO == null) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline");
		headers.set(HttpHeaders.CONTENT_TYPE,
				this.mimeTypeMap.getOrDefault(loadVO.getExtendName().toLowerCase(), "application/octet-stream"));

		return ResponseEntity.ok().headers(headers).body(loadVO.getResource());
	}
}
