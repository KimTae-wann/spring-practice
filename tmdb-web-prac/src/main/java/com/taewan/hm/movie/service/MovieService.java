package com.taewan.hm.movie.service;

import com.taewan.hm.movie.vo.SearchResultVO;
import com.taewan.hm.movie.vo.request.WriteVO;

public interface MovieService {

	SearchResultVO findAllMovie();

	boolean createNewMovie(WriteVO writeVO);
}
