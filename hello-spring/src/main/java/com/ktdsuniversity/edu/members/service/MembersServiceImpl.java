package com.ktdsuniversity.edu.members.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService{

	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMembers(MembersVO membersVO) {
		int insertCount = this.membersDao.insertNewMembers(membersVO);
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
