package com.ktdsuniversity.edu.replies.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.common.utils.ObjectUtils;
import com.ktdsuniversity.edu.common.utils.SessionUtils;
import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.helpers.MultipartFileHandler;
import com.ktdsuniversity.edu.files.vo.request.SearchFileGroupVO;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.DeleteResultVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

@Service
public class RepliesServiceImpl implements RepliesService{

	private static final Logger logger = LoggerFactory.getLogger(RepliesServiceImpl.class);
	
	@Autowired
	private RepliesDao repliesDao;
	
	@Autowired
	private FilesDao filesDao;
	
	@Autowired
	private MultipartFileHandler multipartFileHandler;

	@Transactional
	@Override
	public UpdateResultVO updateReply(UpdateVO updateVO) {
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(updateVO.getId());
		if (ObjectUtils.isNotNull(reply)) {
			if (!SessionUtils.isMineResource(reply.getEmail())) {
				throw new HelloSpringApiException("권한이 부족합니다.", HttpStatus.BAD_REQUEST.value(), "자신의 댓글이 아닙니다.");
			}
		}
		
		updateVO.setFileGroupId(reply.getFileGroupId());
		
		// 선택한 파일들만 삭제
		if (updateVO.getDelFileNum() != null && updateVO.getDelFileNum().size() > 0) {
			// 선택한 파일들의 정보를 조회 --> 파일의 경로 --> 실제 파일을 제거
			SearchFileGroupVO searchFileGroupVO = new SearchFileGroupVO();
			searchFileGroupVO.setDeleteFileNum(updateVO.getDelFileNum());
			searchFileGroupVO.setFileGroupId(updateVO.getFileGroupId());
			List<String> deleteTargets = this.filesDao.selectFilesByFileGroupFileNums(searchFileGroupVO);
					
			// 선택한 파일들을 FILES 테이블에서 제거
			for (String target: deleteTargets) {
				new File(target).delete();
			}
			
			int deleteCount = this.filesDao.deleteFilesByFileGroupIdAndFileNums(searchFileGroupVO);
			logger.debug("삭제한 파일 데이터의 수: {}", deleteCount);
			//System.out.println("삭제한 파일 데이터의 수: " + deleteCount);
		}
		List<MultipartFile> attachFiles = updateVO.getNewAttachFile();
		
		String fileGroupId = updateVO.getFileGroupId();
		
		// 수정하려는 게시글에 첨부파일이 없을 때
		if (fileGroupId == null || fileGroupId.length() == 0) {
			fileGroupId = this.multipartFileHandler.upload(attachFiles);
			updateVO.setFileGroupId(fileGroupId);
		} else {
			this.multipartFileHandler.upload(attachFiles, updateVO.getFileGroupId());
		}
		int updateCount = this.repliesDao.updateReplyByReplyId(updateVO);
		
		UpdateResultVO result = new UpdateResultVO();
		result.setId(updateVO.getId());
		result.setUpdate(updateCount == 1);
		
		return result;
	}
	
	@Override
	public DeleteResultVO deleteRepliesByReplyId(String replyId) {
		
		RepliesVO reply= this.repliesDao.selectReplyByReplyId(replyId);
		if (ObjectUtils.isNotNull(reply)) {
			if (!SessionUtils.isMineResource(reply.getEmail())) {
				throw new HelloSpringApiException("권한이 부족합니다.", HttpStatus.BAD_REQUEST.value(), "자신의 댓글이 아닙니다.");
			}
		} 

		int deleteCount = this.repliesDao.deleteRepliesByReplyId(replyId);
		if (deleteCount == 1) {
			DeleteResultVO result = new DeleteResultVO();
			result.setId(replyId);
			return result;
		}
		return null;
	}
	
	@Override
	public RecommendResultVO increaseRepliesRecommendCountByReplyId(String replyId) {
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(replyId);
		if (ObjectUtils.isNotNull(reply)) {
			if (!SessionUtils.isMineResource(reply.getEmail())) {
				throw new HelloSpringApiException("권한이 부족합니다.", HttpStatus.BAD_REQUEST.value(), "자신의 댓글은 추천할 수 없습니다.");
			}
		}
		int updateCount = this.repliesDao.updateRepliesCntByReplyId(replyId);
		if (updateCount == 1) {
			RecommendResultVO result = this.repliesDao.selectRecommendResultByReplyId(replyId);
			result.setId(replyId);
			result.setRecommendCnt(updateCount);
			return result;
		}
		return null;
	}
	
	@Override
	public SearchResultVO findRepliesCountByArticleId(String articleId) {
		
		SearchResultVO searchResultVO = new SearchResultVO();
		
		int count = this.repliesDao.selectRepliesCountByArticleId(articleId);
		searchResultVO.setCount(count);
		
		if (count > 0) {
			List<RepliesVO> searchList = this.repliesDao.selectRepliesByArticleId(articleId);
			searchResultVO.setResult(searchList);
		}
		return searchResultVO;
	}

	
	@Transactional
	@Override
	public 	RepliesVO createNewReply(CreateVO createVO) {

		String fileGroupId = this.multipartFileHandler.upload(createVO.getAttachFile());
		createVO.setFileGroupId(fileGroupId);
		
		int insertCount = this.repliesDao.insertNewReply(createVO);
		
		if (insertCount == 1) { // 정상 등록
			RepliesVO insertResult = this.repliesDao.selectReplyByReplyId(createVO.getId());
			return insertResult;
		} 
		
		return null;
	}

}
