package com.ktdsuniversity.edu.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.movie.dao.MovieDao;
import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;
import com.ktdsuniversity.edu.utils.FileHandler;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private FileHandler fileHandler;

	@Override
	public List<MovieListVO> readAllMovie() {
		List<MovieListVO> movieList = this.movieDao.selectAllMovie();

		return movieList;
	}

	@Override
	public OneMovieVO readMovieById(String movieId) {
		OneMovieVO oneMovieById = this.movieDao.selectMovieById(movieId);

		return oneMovieById;
	}

	@Override
	public boolean insertMovie(InsertVO insertVO) {
		int insertSuccessCount = this.movieDao.insertMovie(insertVO);

		this.fileHandler.uploadOneFile(insertVO.getFile(), insertVO.getMovieId());

		return insertSuccessCount == 1;
	}
}
