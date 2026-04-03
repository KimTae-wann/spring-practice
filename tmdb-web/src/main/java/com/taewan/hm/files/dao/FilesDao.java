package com.taewan.hm.files.dao;

import org.apache.ibatis.annotations.Mapper;

import com.taewan.hm.files.vo.request.UploadPosterVO;

@Mapper
public interface FilesDao {

	int insertPosterImageFile(UploadPosterVO uploadPosterVO);

}
