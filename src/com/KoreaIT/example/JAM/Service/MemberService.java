package com.KoreaIT.example.JAM.Service;

import java.sql.Connection;

import com.KoreaIT.example.JAM.Dao.MemberDao;

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

}
