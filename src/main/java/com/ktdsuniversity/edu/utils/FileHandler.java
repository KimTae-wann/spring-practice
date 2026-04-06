package com.ktdsuniversity.edu.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.vo.request.InsertVO;

@Component
public class FileHandler {

	@Autowired
	private FileDao fileDao;

	public void uploadOneFile(MultipartFile file, String fileGroupId) {
		if (file != null /* 이거 필요할까? && file.getSize() > 0 */) {
			String obfuscateName = UUID.randomUUID().toString();

			File storeFile = new File("C:\\dev_programs\\test", obfuscateName);
			if (!storeFile.getParentFile().exists()) {
				storeFile.getParentFile().mkdirs();
			}

			try {
				file.transferTo(storeFile);

				String fileName = file.getOriginalFilename();
				String extendName = fileName.substring(fileName.lastIndexOf(".") + 1);

				InsertVO insertVO = new InsertVO();
				insertVO.setFileGroupId(fileGroupId);
				insertVO.setObfuscateName(obfuscateName);
				insertVO.setDisplayName(fileName);
				insertVO.setExtendName(extendName);
				insertVO.setFileLength(storeFile.length());
				insertVO.setFilePath(storeFile.getAbsolutePath());

				this.fileDao.insertFile(insertVO);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
