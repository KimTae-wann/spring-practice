package com.ktdsuniversity.edu.files.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.files.vo.request.SearchFileGroupVO;
import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;
import com.ktdsuniversity.edu.files.vo.response.DownloadVO;

@Mapper
public interface FilesDao {

	int insertAttachFile(UploadVO uploadVO);

	DownloadVO selectFilesByFileGroupIdAndFileNum(SearchFileVO searchFileVO);
	
	List<String> selectFilesByFileGroupFileNums(SearchFileGroupVO searchFileGroupVO);
	
	int deleteFilesByFileGroupIdAndFileNums(SearchFileGroupVO searchFileGroupVO);

	List<String> selectFilePathByFileGroupId(String id);

	String selectNewFileGroupId();

	void insertFileGroupId(String fileGroupId);
}
