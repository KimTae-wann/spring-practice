package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

@Mapper
public interface MembersDao {
	int insertNewMembers(WriteVO wrtieVO);

	MembersVO selectMemberByUserEmail(String userEmail);

	int updateMemberByUserEmail(MembersVO membersVO);

	int deleteMemberByUserEmail(String email);

	int selectMemberCount();

	List<MembersVO> selectMemberList();

	int updateIncreaseLoginFailCount(String email);

	int updateBlock(String email);

	int updateSuccessLogin(LoginVO loginVO);

}
