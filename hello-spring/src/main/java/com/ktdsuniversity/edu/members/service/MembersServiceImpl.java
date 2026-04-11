package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.helpers.SHA256Util;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService{

	private static final Logger logger = LoggerFactory.getLogger(MembersServiceImpl.class);
	
	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMembers(WriteVO writeVO) {
		// 확인해보고 실제로 저장할 때 진짜 있는지 확인(그 사이 누군가 만들었을수도 있잖아)
		MembersVO membersVO = this.membersDao.selectMemberByUserEmail(writeVO.getEmail());
		if (membersVO != null) {
			//throw new IllegalArgumentException(writeVO.getEmail() + "은 이미 사용중입니다");
			throw new HelloSpringException("이미 사용중인 이메일입니다", "members/regi", writeVO);
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
		logger.debug("생성된 회원의 수? {}", insertCount);
		//System.out.println("생성된 회원의 수? " + insertCount);
		if (insertCount == 1) {
			logger.debug("성공");
//			System.out.println("성공");
		} else {
			logger.debug("실패");
//			System.out.println("실패");
			
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
	
	@Transactional(noRollbackFor = HelloSpringException.class)
	@Override
	public MembersVO findMemberByEmailAndPassword(LoginVO loginVO) {
		
		// 1. Email을 이용해 회원 정보 조회하기 (selectMemberByEmail)
		MembersVO membersVO = this.membersDao.selectMemberByUserEmail(loginVO.getEmail());
		
		// 2. 조회된 결과가 없다면 "이메일 또는 비밀번호가 잘못되었습니다." 예외 던지기
		if (membersVO == null) {
			//throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
			throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다.", "members/login", loginVO);
		}
		
		if (membersVO.getBlockYn().equals("Y")) {
			// 로그인 Block 된 시간으로부터 120분이 지나면 다시 로그인 가능한 상태로 변경한다.
			String latestLoginFailDate = membersVO.getLatestLoginFailDate();
			
			DateTimeFormatter dateTimePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime latestBlockDateTime = LocalDateTime.parse(latestLoginFailDate, dateTimePattern);
			// 이 경우엔 예외를 던지지 않도록 한다.
			if (latestBlockDateTime.isAfter(LocalDateTime.now().minusMinutes(120))) {
				//throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
				throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다.", "members/login",loginVO);
			}
		}
		
		// 3. 조회된 결과가 있다면 사용자가 전송한 비밀번호와 조회된 회원의 salt를 이용해 SHA 암호화 하기
		String inputPassword = loginVO.getPassword();
		String storedSalt = membersVO.getSalt();
		String encryptedPassword = SHA256Util.getEncrypt(inputPassword, storedSalt);
		
		// 4. 3에서 암호화 한 비밀번호와 1에서 조회한 비밀번호가 일치하는지 확인하기
		// 5. 비밀번호가 일치하지 않는다면 "이메일 또는 비밀번호가 잘못되었습니다." 예외 던지기
		if (!encryptedPassword.equals(membersVO.getPassword())) {
			
			// 해당 이메일의 로그인 실패 횟수를 1 증가시키고
			// 최근 로그인 실패 날짜를 현재 날짜와 시간으로 변경한다.
			this.membersDao.updateIncreaseLoginFailCount(loginVO.getEmail());
			
			// 최근 로그인 실패 횟수가 5 이상이라면 block-yn을 Y로 변경한다.
			this.membersDao.updateBlock(loginVO.getEmail());
			
			//throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
			throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다.", "members/login", loginVO);
		}
		// 로그인 성공하면 비밀번호 실패 횟수 리셋
		// 1. login_fail_count를 0으로 초기화
		// 2. latest_login_ip를 현재 아이피로 변경
		// 3. login_date를 현재 시간으로 변경
		// 4. block_yn을 'N'으로 변경
		this.membersDao.updateSuccessLogin(loginVO);

		// 6. 비밀번호가 일치하면 1에서 조회한 결과를 반환
		return membersVO;
	}
}
