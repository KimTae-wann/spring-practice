package com.taewan.hm.files.dao;

import org.apache.ibatis.annotations.Mapper;

import com.taewan.hm.files.vo.request.PosterVO;
import com.taewan.hm.files.vo.response.LoadPosterVO;

@Mapper
public interface FilesDao {

	int insertPosterImageFile(PosterVO posterVO);

	LoadPosterVO selectPosterImgByMovieId(String movieId);

	String selectFilePathByFileGroupId(String movieId);

	boolean deleteFileByFileGroupId(String id);
	
}
