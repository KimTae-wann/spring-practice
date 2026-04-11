package com.taewan.hm.movie.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taewan.hm.files.dao.FilesDao;
import com.taewan.hm.files.helpers.MultipartFileHandler;
import com.taewan.hm.movie.dao.MovieDao;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;
import com.taewan.hm.movie.vo.request.WriteVO;

@Service
public class MovieServiceImpl implements MovieService{

	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private FilesDao filesDao;
	
	@Autowired
	private MultipartFileHandler multipartFileHandler;
	
	@Override
	public SearchResultVO findAllMovie() {
		
		SearchResultVO result = new SearchResultVO();
		
		int count = this.movieDao.selectMovieCount();
		result.setCount(count);
		
		if (count == 0) {
			return result;
		}
		
		List<MovieVO> list = this.movieDao.selectMovieList();
		
		result.setResult(list);
		
		return result;
	}
	
	@Override
	public boolean createNewMovie(WriteVO writeVO) {
		int insertCount = this.movieDao.insertNewMovie(writeVO);
		
		this.multipartFileHandler.uploadPosterImgFile(writeVO.getPosterImgFile(), writeVO.getMovieId());
		
		System.out.println("등록된 영화의 개수? " + insertCount);
		return insertCount == 1;
	}
	
	@Override
	public boolean deleteMovieByMovieID(String id) {
		int deleteCount = this.movieDao.deleteMovieById(id);
		
		String filePath = this.filesDao.selectFilePathByFileGroupId(id);
		if (filePath != null) {
			boolean deleteFileResult = this.filesDao.deleteFileByFileGroupId(id);
			logger.debug("파일 삭제 성공 ? {}", deleteFileResult);
			new File(filePath).delete();
		}
		
		return deleteCount == 1;
	}
}
