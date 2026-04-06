package com.ktdsuniversity.edu.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.category.dao.CategoryDao;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<String> readAllCategoryNameByMovieId(String movieId) {
		List<String> categoryNameListById = this.categoryDao.readAllCategoryNameByMovieId(movieId);
		return categoryNameListById;
	}

}
