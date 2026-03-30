package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.MembersVO;

@Mapper
public interface MembersDao {
	int insertNewMembers(MembersVO membersVO);

	MembersVO selectMemberByUserEmail(String userEmail);

	int updateMemberByUserEmail(MembersVO membersVO);

	int deleteMemberByUserEmail(String email);

	int selectMemberCount();

	List<MembersVO> selectMemberList();
}
