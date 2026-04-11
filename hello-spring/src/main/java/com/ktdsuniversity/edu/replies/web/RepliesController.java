package com.ktdsuniversity.edu.replies.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.DeleteResultVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

import jakarta.validation.Valid;

@Controller
public class RepliesController {

	private static final Logger logger = LoggerFactory.getLogger(RepliesController.class);
	
	@Autowired
	private RepliesService repliesService;
	
	// TODO 댓글 수정 구현
	@ResponseBody
	@PostMapping("/api/replies/{replyId}")
	public UpdateResultVO doUpdateReplyByReplyId(@PathVariable String replyId
												,@Valid UpdateVO updateVO // form data 로 전송하는 것은 @ModelAttribute 쓰지 않는다. JSON으로 전송만 써준다.
												,BindingResult bindingResult) { 
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), errors);
		}
		
		updateVO.setId(replyId);
		
		UpdateResultVO updateResult = this.repliesService.updateReply(updateVO);
		
		return updateResult;
	}
	
	@ResponseBody
	@GetMapping("/api/replies/delete/{replyId}")
	public DeleteResultVO doDeleteReplyByReplyId(@PathVariable String replyId
												, @SessionAttribute("__LOGIN_DATA__") MembersVO loginMember) {
		DeleteResultVO deleteResultVO = this.repliesService.deleteRepliesByReplyId(replyId);
		return deleteResultVO;
	}
	
	@ResponseBody
	@GetMapping("/api/replies/recommend/{replyId}")
	public RecommendResultVO doUpdateRecommendCount(@PathVariable String replyId) {
		RecommendResultVO recommendResultVO = this.repliesService.increaseRepliesRecommendCountByReplyId(replyId);
		return recommendResultVO;
	}
	
	
	@ResponseBody
	@GetMapping("/api/replies/{articleId}") // 게시글에 달린 댓글의 목록
	public SearchResultVO getRepliesList(@PathVariable String articleId) {
		SearchResultVO searchResult = this.repliesService.findRepliesCountByArticleId(articleId);
		return searchResult;
	}
	
	@ResponseBody
	@PostMapping("/api/replies-with-file")
	public RepliesVO doCreateNewReplyWithFileAction(
						@Valid CreateVO createVO
						, BindingResult bindingResult
						, @SessionAttribute("__LOGIN_DATA__") MembersVO loginMember) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors =  bindingResult.getFieldErrors(); // 멤버 변수의 에러
			throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), errors);
			//bindingResult.getAllErrors();
		}
		
		createVO.setEmail(loginMember.getEmail());
		
		logger.debug("Reply: {}", createVO.getReply());
		logger.debug("Email: {}", createVO.getEmail());
		logger.debug("ArticleId: {}", createVO.getArticleId());
		logger.debug("ParentReplyId; {}", createVO.getParentReplyId());
		
		RepliesVO reply = this.repliesService.createNewReply(createVO);
		
		return reply;
	}
	
	
	// AJAX(API)요청 / 반환
	// 요청 데이터 + 반환 데이터 ==> JSON
	@ResponseBody
	@PostMapping("/api/replies")
	public RepliesVO doCreateNewReplyAction(@RequestBody @Valid CreateVO createVO // form으로 보내지 않을 것이기 때문에 @ModelAttribute는 필요없다
											,BindingResult bindingResult
											, @SessionAttribute("__LOGIN_DATA__") MembersVO loginMember) {
		
		if (bindingResult.hasErrors()) {
			List<FieldError> errors =  bindingResult.getFieldErrors(); // 멤버 변수의 에러
			throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), errors);
			//bindingResult.getAllErrors();
		}
		
		createVO.setEmail(loginMember.getEmail());
		
		logger.debug("Reply: {}", createVO.getReply());
		logger.debug("Email: {}", createVO.getEmail());
		logger.debug("ArticleId: {}", createVO.getArticleId());
		logger.debug("ParentReplyId; {}", createVO.getParentReplyId());
		
		RepliesVO reply = this.repliesService.createNewReply(createVO);
		
		return reply;
	}
	
}
