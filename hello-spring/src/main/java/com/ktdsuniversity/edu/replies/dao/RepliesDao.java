package com.ktdsuniversity.edu.replies.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;

@Mapper
public interface RepliesDao {

	int insertNewReply(CreateVO createVO);

	RepliesVO selectReplyByReplyId(String id);

	int selectRepliesCountByArticleId(String articleId);

	List<RepliesVO> selectRepliesByArticleId(String articleId);

	int updateRepliesCntByReplyId(String replyId);

	RecommendResultVO selectRecommendResultByReplyId(String replyId);

	int deleteRepliesByReplyId(String replyId);

	int updateReplyByReplyId(UpdateVO updateVO);

}
