package com.taewan.hm.movie.web;

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
	public String doWriteAction(@Valid @ModelAttribute WriteVO writeVO
								, BindingResult bindingResult
								, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("inputData", writeVO);
			return "movie/write";
		}
		
		// XSS 공격을 방어하기 위함
		// <script>alert('해킹');</script> 같은 위험한 코드를 입력했을 때, 이를 실행되지 않는 일반 문자열로 무력화
		// 여러가지 문제 발생 가능
		// DB에 size 크기를 넘어서거나 화면에서 동작이 의도대로 안되거나
//		String posterUrl = writeVO.getPosterUrl();
//		posterUrl = posterUrl.replace("<", "&lt;").replace(">", "&gt");
//		writeVO.setPosterUrl(posterUrl);
//		
//		String title = writeVO.getTitle();
//		title = title.replace("<", "&lt;").replace(">", "&gt");
//		writeVO.setTitle(title);
		
		boolean createResult = this.movieService.createNewMovie(writeVO);

		System.out.println("영화 등록 성공? " + createResult);
		return "redirect:/list";
	}
}