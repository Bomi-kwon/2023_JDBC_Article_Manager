package com.KoreaIT.example.JAM.Service;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.example.JAM.Dao.MemberDao;
import com.KoreaIT.example.JAM.dto.Member;

public class MemberService {
	
	private MemberDao memberDao;
	Connection conn;
	
	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
		this.conn = conn;
	}

	public boolean isLoginIdDup(String loginID) {
		
		return memberDao.isLoginIdDup(loginID);
	}

	public void dojoin(String loginID, String loginPW, String name) {
		memberDao.dojoin(loginID, loginPW, name);
	}

	public Member getMemberByLoginID(String loginID) {
		
		Map<String, Object> memberMap = memberDao.getMemberByLoginID(loginID);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		
		return new Member(memberMap);
	}

}
