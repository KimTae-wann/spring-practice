package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;

// 해야할 일만 적어주면된다. 다른건 MyBatis가 다 만들어서 해줌

@Mapper
public interface BoardDao {

	int selectBoardCount();

	List<BoardVO> selectBoardList();

	int insertNewBoard(WriteVO writeVO);

	BoardVO selectBoardById(String articleId);

	int updateViewCntIncreaseById(String articleId);
	
	int deleteBoardById(String articleId);

	int updateBoardById(UpdateVO updateVO);
	
}
