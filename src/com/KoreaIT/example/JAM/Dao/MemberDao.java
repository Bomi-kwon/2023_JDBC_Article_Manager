package com.KoreaIT.example.JAM.Dao;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberDao {
	Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	public boolean isLoginIdDup(String loginID) {
		
		SecSql sql = new SecSql();
		
		sql.append("SELECT COUNT(loginID) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginID = ?", loginID);
		
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public void dojoin(String loginID, String loginPW, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginID = ?", loginID);
		sql.append(", loginPW = ?", loginPW);
		sql.append(", name = ?", name);

		DBUtil.insert(conn, sql);
	}

	public Map<String, Object> getMemberByLoginID(String loginID) {
		
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginID = ?", loginID);

		return DBUtil.selectRow(conn, sql);
	}
}
