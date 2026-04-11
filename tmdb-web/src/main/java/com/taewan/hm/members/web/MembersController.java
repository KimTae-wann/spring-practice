package com.taewan.hm.members.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taewan.hm.members.service.MembersService;
import com.taewan.hm.members.vo.MembersVO;
import com.taewan.hm.members.vo.request.RegistVO;
import com.taewan.hm.members.vo.response.DuplicateResultVO;

import jakarta.validation.Valid;

@Controller
public class MembersController {
	
	@Autowired
	private MembersService memberService;
	
	@ResponseBody
	@GetMapping("/regist/check/duplicate/{email}")
	public DuplicateResultVO doCheckDuplicateEmail(@PathVariable String email) {
		
		// email이 이미 사용중인지 확인
		MembersVO membersVO = this.memberService.findMembersByUserEmail(email);
		
		// 확인한 결과를 브라우저에게 JSON으로 전송
		// 이미 사용중 ==> true
		// 사용중이지 않음 ==> false
		DuplicateResultVO result = new DuplicateResultVO();
		result.setEmail(email);
		result.setDuplicate(membersVO != null);
		return result;
	}
	
	@GetMapping("/regist")
	public String viewRegistPage() {
		return "members/regist";
	}
	
	@PostMapping("/regist")
	public String doRegistAction(@Valid @ModelAttribute RegistVO registVO
								, BindingResult bindingResult
								, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("inputData", registVO);
			return "members/regist";
		}
		
		boolean createResult = this.memberService.createNewMember(registVO);
		System.out.println("회원 가입 결과? " + createResult);
		return "redirect:/list";
	}
}
