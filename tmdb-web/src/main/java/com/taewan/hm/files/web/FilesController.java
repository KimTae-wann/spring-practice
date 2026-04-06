package com.taewan.hm.files.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taewan.hm.files.service.FilesService;
import com.taewan.hm.files.vo.response.LoadPosterVO;

@Controller
public class FilesController {

	@Autowired
	private FilesService fileService;
	
private Map<String, String> mimeTypeMap;
	
	public FilesController() {
		this.mimeTypeMap = new HashMap<>();
		this.mimeTypeMap.put("txt", "text/plain");
		
		// Image
		this.mimeTypeMap.put("jpg", "image/jpg");
		this.mimeTypeMap.put("jpeg", "image/jpeg");
		this.mimeTypeMap.put("png", "image/png");
		this.mimeTypeMap.put("webp", "image/webp");
		this.mimeTypeMap.put("svg", "image/svg");
		
		// Static Resource
		this.mimeTypeMap.put("css", "text/css");
		this.mimeTypeMap.put("js", "text/javascript");
		this.mimeTypeMap.put("html", "text/html");

		// MS Office
		this.mimeTypeMap.put("csv", "text/csv");
		this.mimeTypeMap.put("xls", "application/vnd.ms-excel");
		this.mimeTypeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		this.mimeTypeMap.put("ppt", "application/vnd.ms-powerpoint");
		this.mimeTypeMap.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		
		this.mimeTypeMap.put("zip", "application/zip");
		this.mimeTypeMap.put("tar", "application/x-tar");
	}
	
	@GetMapping("/file/{movieId}")
	public ResponseEntity<Resource> doLoadPosterURL(@PathVariable String movieId) {
		LoadPosterVO loadPosterVO = this.fileService.readPosterImgByMovieId(movieId);
		
		if (loadPosterVO == null) {
			return ResponseEntity.notFound().build();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline");
		headers.set(HttpHeaders.CONTENT_TYPE,
				this.mimeTypeMap.getOrDefault(loadPosterVO.getExtendName().toLowerCase(), "application/octet-stream"));
		
		return ResponseEntity.ok()
						     .headers(headers)
						     .body(loadPosterVO.getResource());
	}
}
