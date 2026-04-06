package com.taewan.hm.files.service;

import com.taewan.hm.files.vo.response.LoadPosterVO;

public interface FilesService {

	LoadPosterVO readPosterImgByMovieId(String movieId);
}
