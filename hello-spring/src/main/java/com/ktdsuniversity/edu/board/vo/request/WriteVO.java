package com.ktdsuniversity.edu.board.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 게시글 등록을 위해
 * 브라우저에서 컨트롤러(엔드포인트)로 전송되는
 * 파라미터를 받아오기 위한 클래스
 */

public class WriteVO {
	
	// 얘는 어차피 DB에서 Sequence로 가져오니까 NotNULL 처리 됨
	private String id;
	@NotBlank(message = "제목은 반드시 입력해주세요.")
	@Size(min = 3, message = "제목은 최소 3글자 이상 입력해주세요.")
	private String subject;
	@NotBlank(message = "이메일은 반드시 입력해주세요.")
	@Email(message="이메일 형태가 아닙니다.")
	private String email;

	private String content;
	
	private List<MultipartFile> attachFile; // 업로드한 파일은 항상 MultipartFile로 가져온다

	private String FileGroupId;
	
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public List<MultipartFile> getAttachFile() {
		return this.attachFile;
	}

	public void setAttachFile(List<MultipartFile> attachFile) {
		this.attachFile = attachFile;
	}

	public String getFileGroupId() {
		return this.FileGroupId;
	}

	public void setFileGroupId(String fileGroupId) {
		FileGroupId = fileGroupId;
	}
}
