package com.taewan.hm.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taewan.hm.members.dao.MembersDao;
import com.taewan.hm.members.helpers.SHA256Util;
import com.taewan.hm.members.vo.MembersVO;
import com.taewan.hm.members.vo.request.RegistVO;

@Service
public class MembersServiceImpl implements MembersService{
	
	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMember(RegistVO registVO) {
		// 확인 먼저 해보고 실제로 저장할 때 정말 있는지 2차 확인 (사이에 만들었을 수도 있기 때문)
		MembersVO membersVO = this.membersDao.selectMemberByUserEmail(registVO.getEmail());
		if (membersVO != null) {
			throw new IllegalArgumentException(registVO.getEmail() + "은 이미 사용중입니다");
		}
		
		// 암호화를 위한 비밀키 생성
		String newSalt = SHA256Util.generateSalt();
		String usersPassword = registVO.getPassword();
		
		usersPassword = SHA256Util.getEncrypt(usersPassword, newSalt);
		
		registVO.setSalt(newSalt);
		registVO.setPassword(usersPassword);
		
		int insertCount = this.membersDao.insertNewMember(registVO);
		return insertCount == 1;
	}

	@Override
	public MembersVO findMembersByUserEmail(String userEmail) {
		MembersVO member = this.membersDao.selectMemberByUserEmail(userEmail);
		return member;
	}
}
