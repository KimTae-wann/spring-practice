package com.ktdsuniversity.edu.movie.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktdsuniversity.edu.category.service.CategoryService;
import com.ktdsuniversity.edu.movie.service.MovieService;
import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;

@Controller
@RequestMapping("/")
public class MovieController {
//	기능		Controller		Service 		Mapper/DAO
//	조회 목록	view이름List		readAll이름		selectAll이름
//	조회 상세	view이름			read이름ById		select이름ById
//	생성 		doInsert이름		insert이름		insert이름
//	수정		doUpdate이름		update이름		update이름
//	삭제		doDelete이름		delete이름		delete이름
	@Autowired
	private MovieService movieService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String viewMovieList(Model model) {
		List<MovieListVO> movieList = this.movieService.readAllMovie();

		model.addAttribute("movieList", movieList);
		return "movie/list";
	}

	@GetMapping("view/{movieId}")
	public String viewMovieById(@PathVariable String movieId, Model model) {
		OneMovieVO oneMovieById = this.movieService.readMovieById(movieId);
		List<String> categoryNameListById = this.categoryService.readAllCategoryNameByMovieId(movieId);

		model.addAttribute("movie", oneMovieById);
		model.addAttribute("categoryNameList", categoryNameListById);
		return "movie/view";
	}

	@GetMapping("insert")
	public String doInsert() {
		return "movie/insert";
	}

	@PostMapping("insert")
	public String doInsert(InsertVO insertVO) {
		boolean isSuccess = this.movieService.insertMovie(insertVO);

		if (isSuccess) {
			return "redirect:";
		} else {
			return "errer/404";
		}
	}
}
