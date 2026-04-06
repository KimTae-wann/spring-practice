package com.taewan.hm.movie.web;

import com.taewan.hm.files.web.FilesController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.taewan.hm.movie.service.MovieService;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;
import com.taewan.hm.movie.vo.request.WriteVO;

import jakarta.validation.Valid;

@Controller
public class MovieController {
	
	private final FilesController filesController;
	@Autowired
	private MovieService movieService;

	MovieController(FilesController filesController) {
		this.filesController = filesController;
	}
	
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
	public String doWriteAction(@Valid @ModelAttribute WriteVO writeVO
								, BindingResult bindingResult
								, Model model) {
		// 사용자의 입력값을 검증 했을 때, 에러가 있다면
		if (bindingResult.hasErrors()) {
			// Spring Validator 가 검사를 해서 발생한 에러를 모두 보여준다.
			System.out.println(bindingResult.getAllErrors());
			
			// 브라우저에 "movie/write" 페이지를 보여주도록 하고
			// 해당 페이지에 사용자가 입력한 값을 전달한다.
			model.addAttribute("inputData", writeVO);
			return "movie/write";
		}
		
		boolean createResult = this.movieService.createNewMovie(writeVO);

		System.out.println("영화 등록 성공? " + createResult);
		return "redirect:/list";
	}
}