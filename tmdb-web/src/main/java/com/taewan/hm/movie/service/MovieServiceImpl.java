package com.taewan.hm.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taewan.hm.movie.dao.MovieDao;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
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
	public boolean createNewMovie(MovieVO movieVO) {
		int insertCount = this.movieDao.insertNewMovie(movieVO);
		System.out.println("등록된 영화의 개수? " + insertCount);
		return insertCount == 1;
	}
}
