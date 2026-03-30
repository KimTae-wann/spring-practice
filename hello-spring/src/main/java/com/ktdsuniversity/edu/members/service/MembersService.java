package com.ktdsuniversity.edu.members.service;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.SearchResultVO;

public interface MembersService {

	boolean createNewMembers(MembersVO membersVO);

	MembersVO findMembersByUserEmail(String userEmail);

	boolean updateMemberByUserEmail(MembersVO membersVO);

	boolean deleteMemberByUserEmail(String userEmail);

	SearchResultVO findAllMembers();

}
