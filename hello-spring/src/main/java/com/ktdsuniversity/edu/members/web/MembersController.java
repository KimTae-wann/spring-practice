package com.ktdsuniversity.edu.members.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.SearchResultVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.response.DuplicateResultVO;

import jakarta.validation.Valid;

/**
 * EndPoint 생성/관리
 * + Validation Check
 */


@Controller
public class MembersController {

	@Autowired
	private MembersService membersService;
	
	@ResponseBody // 반환되는 데이터가 JSON으로 바뀐다.
	@GetMapping("/regist/check/duplicate/{email}")
	public DuplicateResultVO doCheckDuplicateEmail(@PathVariable String email) {
		
		// email이 이미 사용중인지 확인한다.
		MembersVO membersVO = this.membersService.findMembersByUserEmail(email);
		
		// 확인한 결과를 브라우저에게 JSON으로 전송한다.
		// 이미 사용중 ==> {email: "test@gmail.com", duplicate: true}
		// 사용중이지 않음 ==> {email: "test@gmail.com", duplicate: false}
		
		DuplicateResultVO result = new DuplicateResultVO();
		result.setEmail(email);
		result.setDuplicate(membersVO != null);
		return result;
	}
	
	
	@GetMapping("/regist")
	public String viewRegiPage() {
		return "members/regi";
	}
	
	
	@PostMapping("/regist")
	public String doRegiAction(@Valid @ModelAttribute WriteVO writeVO
								, BindingResult bindingResult
								, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("inputData", writeVO);
			return "members/regi";
		}
		
		System.out.println(writeVO.getEmail());
		System.out.println(writeVO.getName());
		System.out.println(writeVO.getPassword());
		
		boolean createMember = this.membersService.createNewMembers(writeVO);
		
		System.out.println("회원 생성 성공? " + createMember);
		
		return "redirect:/regist";
	}
	
	/*
	 * /member/view/사용자이메일 --> 회원 정보 조회
	 * /member/update/사용자이메일 --> 회원정보 수정 페이지 보기 
	 * /member/update/사용자이메일 --> 회원정보 수정 하기
	 * /member/delete?id=사용자이메일  --> 회원정보 삭제하기
	 */
	@GetMapping("/member/view/{userEmail}")
	public String viewMemberPage(Model model, @PathVariable String userEmail) {
		MembersVO findResult = this.membersService.findMembersByUserEmail(userEmail);
		
		model.addAttribute("user", findResult);
		
		return "members/view";
	}
	
	@GetMapping("/member/update/{userEmail}")
	public String viewUpdateMemberPage(Model model, @PathVariable String userEmail) {
		
		MembersVO data = this.membersService.findMembersByUserEmail(userEmail);
	
		model.addAttribute("user", data);
		
		return "/members/update";
	}
	
	@PostMapping("/member/update/{userEmail}")
	public String doUpdateMemberAction(MembersVO membersVO, @PathVariable String userEmail) {
		
		membersVO.setEmail(userEmail);
		boolean updateResult = this.membersService.updateMemberByUserEmail(membersVO);
		System.out.println("수정 성공? " + updateResult);
		return "redirect:/member/view/" + userEmail;
	}
	
	@GetMapping("/member/delete")
	public String doDeleteAction(@RequestParam String id) {
		
		boolean updateResult = this.membersService.deleteMemberByUserEmail(id);
		System.out.println("삭제 결과? " + updateResult);
		
		return "redirect:/";
	}
	
	// /member --> 회원들의 목록이 조회되도록 코드를 작성
	//			--> 회원 목록 조회
	//			--> members/list.jsp : 회원 목록 반복.
	//								 : 회원의 수 출력
	//								 : 회원의 수가 없을 때, "등록된 회원이 없습니다" 출력
	//								 : 목록 아래에는 "새로운 회원 등록" 링크 추가
	@GetMapping("/member")
	public String viewMemberPage(Model model) {
		SearchResultVO searchResult = this.membersService.findAllMembers();
		
		List<MembersVO> list = searchResult.getResult();
		int searchCount = searchResult.getCount();
		System.out.println(searchCount);
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		
		return "members/list";
	}
}
