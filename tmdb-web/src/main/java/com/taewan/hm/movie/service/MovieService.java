package com.taewan.hm.movie.service;

import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;

public interface MovieService {

	SearchResultVO findAllMovie();

	boolean createNewMovie(MovieVO movieVO);
}
