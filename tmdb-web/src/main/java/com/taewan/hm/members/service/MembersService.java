package com.taewan.hm.members.service;

import com.taewan.hm.members.vo.MembersVO;
import com.taewan.hm.members.vo.request.RegistVO;

public interface MembersService {

	boolean createNewMember(RegistVO registVO);

	MembersVO findMembersByUserEmail(String email);
}
