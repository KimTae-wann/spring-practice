package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.DeleteResultVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

import jakarta.validation.Valid;


public interface RepliesService {

	RepliesVO createNewReply(CreateVO createVO);

	SearchResultVO findRepliesCountByArticleId(String articleId);

	RecommendResultVO increaseRepliesRecommendCountByReplyId(String replyId);

	DeleteResultVO deleteRepliesByReplyId(String replyId);

	UpdateResultVO updateReply(UpdateVO updateVO);

}
