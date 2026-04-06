package com.ktdsuniversity.edu.files.helpers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Component
public class MultipartFileHandler {

	@Autowired
	private FilesDao filesDao;
	
	// 이미 존재하는 fileGroupId로 insert 또는 새로 발급 받은 fileGroupId로 insert
	public String upload(List<MultipartFile> attachFiles, String fileGroupId) {
		if (attachFiles != null && attachFiles.size() > 0) {
			
			for (int i = 0; i < attachFiles.size(); i++) {
				// 업로드를 하지 않았는데 했다고 판단한 경우에는 다음 반복으로 넘어가라
				if (attachFiles.get(i).isEmpty()) {
					continue;
				}
				
				String obfuscateName = UUID.randomUUID().toString();
				
				File storeFile = new File("C:\\Users\\xodhk\\uploadFiles", obfuscateName);
				// C:\\uploadfiles 폴더가 없으면 생성해라
				if (!storeFile.getParentFile().exists()) {
					storeFile.getParentFile().mkdirs();
				}
				try {
					attachFiles.get(i).transferTo(storeFile);
					// FILES 테이블에 첨부파일 데이터를 INSERT
					UploadVO uploadVO = new UploadVO();
					String fileName = attachFiles.get(i).getOriginalFilename();
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					uploadVO.setFileGroupId(fileGroupId);
					uploadVO.setObfuscateName(obfuscateName);
					uploadVO.setDisplayName(fileName);
					uploadVO.setExtendName(ext);
					uploadVO.setFileLength(storeFile.length());
					uploadVO.setFilePath(storeFile.getAbsolutePath());
					
					this.filesDao.insertAttachFile(uploadVO);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			return fileGroupId; // 파일이 있으면
		}
		return null; // 파일이 없으면
	}
	
	/**
	 * @param attachFiles
	 * @return 첨부파일의 그룹 아이디
	 */
	public String upload(List<MultipartFile> attachFiles) {
		if (attachFiles != null && attachFiles.size() > 0) {
				
			String fileGroupId = this.filesDao.selectNewFileGroupId();
			this.filesDao.insertFileGroupId(fileGroupId);
			
			this.upload(attachFiles, fileGroupId);
			
			return fileGroupId; // 파일이 있으면
		}
		return null; // 파일이 없으면
	}
	
	
}
