package com.taewan.hm.movie.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.taewan.hm.movie.service.MovieService;
import com.taewan.hm.movie.vo.MovieVO;
import com.taewan.hm.movie.vo.SearchResultVO;

@Controller
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/hello")
	public String viewHelloPage() {
		return "hello";
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
	public String doWriteAction(MovieVO movieVO) {
		boolean createResult = this.movieService.createNewMovie(movieVO);

		System.out.println("영화 등록 성공? " + createResult);
		return "redirect:/list";
	}
}

//5. 영화 목록 조회 엔드포인트 생성 ( /list ) - 데이터베이스에서 조회
//
//6. 영화 등록 엔드포인트 생성 ( /write ) - 영화 등록 페이지 보여주기
//
//7. 영화 등록 처리 엔드포인트 생성 ( /write ) - 영화 등록 처리 (insert 수행)
//
//* MOVIE 테이블에서만 조회 합니다.
//
//코드 작성 후 프로젝트를 압축해 업로드 합니다.