package com.taewan.hm.files.helpers;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.taewan.hm.files.dao.FilesDao;
import com.taewan.hm.files.vo.request.PosterVO;

@Component
public class MultipartFileHandler {
	
	@Autowired
	FilesDao filesDao;
	
	public void uploadPosterImgFile(MultipartFile posterImgFile, String fileGroupId) {
		if (posterImgFile != null) {
			String obfuscateName = UUID.randomUUID().toString();

			File storeFile = new File("C:\\Users\\xodhk\\tmdb-image", obfuscateName);
			
			if (!storeFile.getParentFile().exists()) {
				storeFile.getParentFile().mkdirs();
			}
			
			try {
				posterImgFile.transferTo(storeFile);

				String fileName = posterImgFile.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				PosterVO posterVO = new PosterVO();
				posterVO.setFileGroupId(fileGroupId);
				posterVO.setObfuscateName(obfuscateName);
				posterVO.setDisplayName(fileName);
				posterVO.setExtendName(ext);
				posterVO.setFileLength(storeFile.length());
				posterVO.setFilePath(storeFile.getAbsolutePath());
				
				this.filesDao.insertPosterImageFile(posterVO);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
