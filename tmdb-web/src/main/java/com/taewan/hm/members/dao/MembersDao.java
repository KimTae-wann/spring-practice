package com.taewan.hm.members.dao;

import org.apache.ibatis.annotations.Mapper;

import com.taewan.hm.members.vo.MembersVO;
import com.taewan.hm.members.vo.request.RegistVO;

@Mapper
public interface MembersDao {

	int insertNewMember(RegistVO registVO);

	MembersVO selectMemberByUserEmail(String userEmail);
}
