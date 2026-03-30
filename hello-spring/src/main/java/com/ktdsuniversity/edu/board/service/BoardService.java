package com.ktdsuniversity.edu.board.service;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.SearchResultVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;

/**
 * 목적
 * 상황별
 * 	-회원의 등급이 다르다-->일반 사용자, 관리자, 슈퍼관리자, 운영자 등등....
 * 	-애플리케이션의 버전이 다르다 --> 0.0.1, 1.0.2 동시에 사용하는 경우)
 * 로 알맞는 처리를 위해 인터페이스를 제공.
 * 	- 상황에 맞추어 클래스를 생성해서 사용자에게 제공.
 * 
 * Service 의 목적 --> 트랜잭션 처리를 위해 쓰임
 * 				--> 트랜잭션의 다른 표현 --> 업무로직 (Biz Logic)
 */
public interface BoardService {

	SearchResultVO findAllBoard();

	boolean createNewBoard(WriteVO writeVO);

	BoardVO findBoardByArticleId(String articleId, ReadType readType);

	void deleteBoardByArticleId(String articleId);

	boolean updateBoardByArticleId(UpdateVO updateVO);

}
