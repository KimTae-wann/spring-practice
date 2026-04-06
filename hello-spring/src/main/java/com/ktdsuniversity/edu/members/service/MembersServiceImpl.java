package com.ktdsuniversity.edu.members.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.helpers.SHA256Util;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService{

	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMembers(WriteVO writeVO) {
		// 확인해보고 실제로 저장할 때 진짜 있는지 확인(그 사이 누군가 만들었을수도 있잖아)
		MembersVO membersVO = this.membersDao.selectMemberByUserEmail(writeVO.getEmail());
		if (membersVO != null) {
			throw new IllegalArgumentException(writeVO.getEmail() + "은 이미 사용중입니다");
		}
		
		// 암호화를 위한 비밀키 생성
		String newSalt = SHA256Util.generateSalt();
		String usersPassword = writeVO.getPassword();
		// 사용자가 입력한 비밀번호를 newSalt를 이용해 암호화
		// 비밀번호와 newSalt의 값이 일치하면, 항상 같은 값의 암호화 결과가 생성된다.
		usersPassword = SHA256Util.getEncrypt(usersPassword, newSalt);
		
//		String confirmPassword = writeVO.getConfirmPassword();
//		confirmPassword = SHA256Util.getEncrypt(confirmPassword, newSalt);
//		
//		if (usersPassword == confirmPassword) {
//			
//		}
		// 비밀키 저장
		writeVO.setSalt(newSalt);
		// 암호화된 비밀번호 저장
		writeVO.setPassword(usersPassword);
		
		int insertCount = this.membersDao.insertNewMembers(writeVO);
		System.out.println("생성된 회원의 수? " + insertCount);
		if (insertCount == 1) {
			System.out.println("성공");
		} else {
			
			System.out.println("실패");
			
		}
		return insertCount == 1;
	}
	
	@Override
	public MembersVO findMembersByUserEmail(String userEmail) {
		MembersVO member = this.membersDao.selectMemberByUserEmail(userEmail);
		return member;
	}
	@Override
	public boolean updateMemberByUserEmail(MembersVO membersVO) {
		int result = this.membersDao.updateMemberByUserEmail(membersVO);
		return result == 1;
	}
	
	@Override
	public boolean deleteMemberByUserEmail(String email) {
		int result = this.membersDao.deleteMemberByUserEmail(email);
		return result == 1;
	}
	
	@Override
	public SearchResultVO findAllMembers() {
		SearchResultVO result = new SearchResultVO();
		
		int count = this.membersDao.selectMemberCount();
		result.setCount(count);
		
		if (count == 0) {
			return result;
		}
		
		List<MembersVO> list = this.membersDao.selectMemberList();
		
		result.setResult(list);
		
		return result;
	}
}
