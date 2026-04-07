package com.ktdsuniversity.edu.board.vo;

import java.util.List;

import com.ktdsuniversity.edu.files.vo.FilesVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

public class BoardVO {

	private String id;
	private String subject;
	private String content;
	private String email;
	private int viewCnt;
	private String crtDt;
	private String mdfyDt;
	private String fileName;
	private String originFileName;
	private String fileGroupId;
	
	private List<FilesVO> files;
	
	private MembersVO membersVO;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getViewCnt() {
		return viewCnt;
	}
	
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	
	public String getCrtDt() {
		return crtDt;
	}
	
	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}
	
	public String getMdfyDt() {
		return mdfyDt;
	}
	
	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getOriginFileName() {
		return originFileName;
	}
	
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	
	public List<FilesVO> getFiles() {
		return this.files;
	}
	
	public void setFiles(List<FilesVO> files) {
		this.files = files;
	}
	
	public MembersVO getMembersVO() {
		return this.membersVO;
	}

	public void setMembersVO(MembersVO membersVO) {
		this.membersVO = membersVO;
	}
}
