package com.taewan.hm.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taewan.hm.files.helpers.MultipartFileHandler;
import com.taewan.hm.movie.dao.MovieDao;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;
import com.taewan.hm.movie.vo.request.WriteVO;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
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
}
