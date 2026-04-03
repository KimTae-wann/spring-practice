package com.taewan.hm.movie.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taewan.hm.files.dao.FilesDao;
import com.taewan.hm.files.vo.request.UploadPosterVO;
import com.taewan.hm.movie.dao.MovieDao;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;
import com.taewan.hm.movie.vo.request.WriteVO;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private FilesDao filesDao;
	
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
		
		MultipartFile posterImgFile = writeVO.getPosterImgFile();

		String obfuscateName = UUID.randomUUID().toString();

		File storeFile = new File("C:\\Users\\xodhk\\tmdb-image", obfuscateName);
		
		if (!storeFile.getParentFile().exists()) {
			storeFile.getParentFile().mkdirs();
		}
		
		try {
			posterImgFile.transferTo(storeFile);

			UploadPosterVO uploadPosterVO = new UploadPosterVO();
			String fileName = posterImgFile.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			uploadPosterVO.setFileNum(1); // 단일 포스터
			uploadPosterVO.setFileGroupId(writeVO.getMovieId());
			uploadPosterVO.setObfuscateName(obfuscateName);
			uploadPosterVO.setDisplayName(fileName);
			uploadPosterVO.setExtendName(ext);
			uploadPosterVO.setFileLength(storeFile.length());
			uploadPosterVO.setFilePath(storeFile.getAbsolutePath());
			
			this.filesDao.insertPosterImageFile(uploadPosterVO);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("등록된 영화의 개수? " + insertCount);
		return insertCount == 1;
	}
}
