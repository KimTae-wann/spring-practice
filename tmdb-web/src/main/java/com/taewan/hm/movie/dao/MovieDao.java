package com.taewan.hm.movie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.taewan.hm.movie.vo.MovieVO;


@Mapper
public interface MovieDao {

	int selectMovieCount();

	List<MovieVO> selectMovieList();

	int insertNewMovie(MovieVO movieVO);

}
