package com.taewan.hm.movie.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.taewan.hm.movie.service.MovieService;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;
import com.taewan.hm.movie.vo.request.WriteVO;

@Controller
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/list")
	public String viewListPage(Model model) {
		
		SearchResultVO searchResult = this.movieService.findAllMovie();
		
		List<MovieVO> list = searchResult.getResult();
		int searchCount = searchResult.getCount();
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		
		return "movie/list";
	}
	
	@GetMapping("/write")
	public String viewWritePage() {
		return "movie/write";
	}
	
	@PostMapping("/write")
	public String doWriteAction(@ModelAttribute WriteVO writeVO) {
		boolean createResult = this.movieService.createNewMovie(writeVO);

		System.out.println("영화 등록 성공? " + createResult);
		return "redirect:/list";
	}
}