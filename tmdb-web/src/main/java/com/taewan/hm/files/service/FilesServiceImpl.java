package com.taewan.hm.files.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taewan.hm.files.dao.FilesDao;
import com.taewan.hm.files.vo.response.LoadPosterVO;

@Service
public class FilesServiceImpl implements FilesService{

	@Autowired
	private FilesDao filesDao;
	
	@Override
	public LoadPosterVO readPosterImgByMovieId(String movieId) {
		LoadPosterVO loadPosterVO = this.filesDao.selectPosterImgByMovieId(movieId);
		return loadPosterVO;
	}
}
