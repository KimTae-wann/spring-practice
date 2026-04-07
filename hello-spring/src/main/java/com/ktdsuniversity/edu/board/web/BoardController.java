package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.SearchResultVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

import jakarta.validation.Valid;

@Controller // EndPoint 생성
public class BoardController {
	/*
	 * Bean 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다. DI
	 */
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/")
	public String viewListPage(Model model) {
		
		SearchResultVO searchResult = this.boardService.findAllBoard();
		
		// 게시글의 목록을 조회
		List<BoardVO> list = searchResult.getResult();
		// 게시글의 개수 조회
		int searchCount = searchResult.getCount();
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		
		return "board/list";
	}
	
	// 게시글 등록 화면 보여주는 EndPoint
	@GetMapping("/write")
	public String viewWritePage(@SessionAttribute(name="__LOGIN_DATA__", required = false) 
								MembersVO loginMember) {
		// 1. 로그인을 안한 상태에서 글쓰기 페이지로 접근하면 "/list"로 이동 하도록 한다.
		if (loginMember == null) {
			return "redirect:/";
		}
		//session.setMaxInactiveInterval(10); // parameter ==> seconds
		return "board/write";
	}

	
	// request.getSession(); ==> HttpRequestHeader로 전달된 JSESSION의 객체를 반환
	// request.getSession(true); ==> 기존 JSESSIONID로 발급된 세션객체는 버리고, 새로운 ID의 세션객체를 생성 후 반환.
	// --> 이러면 게시글 작성에서 이메일을 안 적어줘도 된다. 서버에서 세션을 전달해 주기 때문이다.
	@PostMapping("/write")
	public String doWriteAction(@Valid @ModelAttribute WriteVO writeVO
								// @Valid의 결과를 받아오는 파라미터
								// 반드시 동일하게 써줘야 한다.
								, BindingResult bindingResult
								, Model model
								, @SessionAttribute(name="__LOGIN_DATE__", required = false)
								MembersVO loginMember) {
		
		// 2. 글 쓰기 페이지에서 30분동안 아무것도 안한 이후 "등록" 버튼을 누르면 "/login"으로 이동 하도록 한다.
		// 세션은 이벤트(엔드포인트 요청)가 발생하면 항상 Default인 30분으로 초기화 된다.
		if (loginMember == null) {
			return "redirect:/login";
		}
		
		// 사용자의 입력값을 검증 했을 때, 에러가 있다면
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors()); // Spring Validator 가 검사를 해서 발생한 에러를 모두 보여준다.
			// 브라우저에 "board/write" 페이지를 보여주도록 하고
			// 해당 페이지에 사용자가 입력한 값을 전달한다
			model.addAttribute("inputData", writeVO);
			return "board/write";
		
		}
		
		// 로그인 데이터(__LOGIN_DATA__)에서 로그인 한 사용자의 이메일을 가져온다 ==> MembersController
		// 반환값이 Object이기 때문에 캐스팅을 해준다.
		writeVO.setEmail(loginMember.getEmail());
		
		// create, update, delete --> 성공 /실패 여부 반환
		boolean createResult = this.boardService.createNewBoard(writeVO);
		System.out.println("등록 성공 ? " + createResult);
		
		// redirect: 브라우저에게 다음 End Point를 요청하도록 지시
		// redirect:/ --> 브라우저에게 "/" endpoint로 이동하도록 지시
		
		return "redirect:/";
	}
	
	// 게시글 내용 조회
	// endpoint -> /view/게시글아이디 예> /view/BO-20260327-000001
	// 해야 하는 역할
	// 1. 게시글 내용을 조회해서 브라우저에게 노출.
	// 2. 조회수 1증가.
	
	// GetMapping 은 항상 view로 시작 Page로 끝남
	
	@GetMapping("/view/{articleId}")
	public String viewDetailPage(Model model, @PathVariable String articleId) {
		
		// articleId 로 데이터베이스에서 게시글을 조회한다.
		
		// 조회할 때 조회수가 하나 증가해야 한다. // 조회는 read 아니면 find 만 씀
		BoardVO findResult = this.boardService.findBoardByArticleId(articleId, ReadType.VIEW);
		
		model.addAttribute("article", findResult);
		
		return "board/view";
	}
	
	// 게시글 내용 삭제
	// endpoint -> /
	@GetMapping("/delete") // local.... 
	public String doDeleteAction(@RequestParam String id, Model model) {
		
		
		boolean deleteResult = this.boardService.deleteBoardByArticleId(id);
		System.out.println("삭제 결과? " + deleteResult);
		return "redirect:/";
	}
	
	@GetMapping("/update/{articleId}")
	public String viewUpdatePage(@PathVariable String articleId
								, Model model
								, @SessionAttribute(name="__LOGIN_DATE__", required = false)
								MembersVO loginMember) {
		BoardVO data = this.boardService.findBoardByArticleId(articleId, ReadType.UPDATE);
		
		
		if (!loginMember.getEmail().equals(data.getEmail())) {
			return "redirect:/login";
		}
		
		model.addAttribute("article", data);
		
		return "board/update";
	}
	
	@PostMapping("/update/{articleId}")
	public String doUpdateAction(UpdateVO updateVO
								, @PathVariable String articleId
								, @SessionAttribute(name="__LOGIN_DATE__", required = false)
								MembersVO loginMember) {
		if (loginMember == null) {
			return "redirect:/login";
		}

		updateVO.setEmail(loginMember.getEmail());
		updateVO.setId(articleId);
		boolean updateResult = this.boardService.updateBoardByArticleId(updateVO);
		System.out.println("업데이트 성공? " + updateResult);
		
		return "redirect:/view/" + articleId;
	}
}
