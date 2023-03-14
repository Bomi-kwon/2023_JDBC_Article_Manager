package com.KoreaIT.example.JAM.Controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberController {
	Connection conn;
	Scanner sc;

	public MemberController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}

	public void dojoin() {
		String loginID = null;
		String loginPW = null;
		String name = null;
		
		System.out.println("== 회원 가입 ==");
		
		while(true) {
			System.out.printf("아이디 : ");
			loginID = sc.nextLine().trim();
			
			if(loginID.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT COUNT(loginID) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginID = ?", loginID);
			
			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);
			
			if(isLoginIdDup) {
				System.out.printf("%s는 이미 사용중인 아이디입니다.\n",loginID);
				continue;
			}
			break;
		}
		
		while(true) {
			System.out.printf("비밀번호 : ");
			loginPW = sc.nextLine().trim();
			
			if(loginPW.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			boolean loginPWCheck = true;
			
			while(true) {
				System.out.printf("비밀번호 확인: ");
				String loginPW_check = sc.nextLine().trim();
				
				if(loginPW_check.length() == 0) {
					System.out.println("비밀번호 확인을 입력해주세요.");
					continue;
				}
				if(!loginPW.equals(loginPW_check)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					loginPWCheck = false;
				}
				break;
			}
			if (loginPWCheck) {
				break;
			}
		}
		while(true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if(name.length() == 0) {
				System.out.println("이름을 입력해주세요.");
				continue;
			}
			break;
		}

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginID = ?", loginID);
		sql.append(", loginPW = ?", loginPW);
		sql.append(", name = ?", name);

		int id = DBUtil.insert(conn, sql);

		System.out.printf("%s 회원님 환영합니다.\n", name);
	}
	
}
