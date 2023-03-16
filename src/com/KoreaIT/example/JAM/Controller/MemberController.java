package com.KoreaIT.example.JAM.Controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Service.MemberService;
import com.KoreaIT.example.JAM.dto.Member;
import com.KoreaIT.example.JAM.session.Session;
import com.KoreaIT.example.JAM.util.Util;

public class MemberController {
	private Scanner sc;
	private MemberService memberService;

	public MemberController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.memberService = new MemberService(conn);
	}

	public void dojoin() {
		
		if (Session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}
		
		String loginID = null;
		String loginPW = null;
		String name = null;

		System.out.println("== 회원 가입 ==");

		while (true) {
			System.out.printf("아이디 : ");
			loginID = sc.nextLine().trim();

			if (loginID.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginID);

			if (isLoginIdDup) {
				System.out.printf("%s는 이미 사용중인 아이디입니다.\n", loginID);
				continue;
			}
			break;
		}

		while (true) {
			System.out.printf("비밀번호 : ");
			loginPW = sc.nextLine().trim();

			if (loginPW.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			boolean loginPWCheck = true;

			while (true) {
				System.out.printf("비밀번호 확인: ");
				String loginPW_check = sc.nextLine().trim();

				if (loginPW_check.length() == 0) {
					System.out.println("비밀번호 확인을 입력해주세요.");
					continue;
				}
				if (!loginPW.equals(loginPW_check)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					loginPWCheck = false;
				}
				break;
			}
			if (loginPWCheck) {
				break;
			}
		}
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("이름을 입력해주세요.");
				continue;
			}
			break;
		}

		memberService.dojoin(loginID, loginPW, name);

		System.out.printf("%s 회원님 환영합니다.\n", name);
	}

	public void dologin() {
		
		if (Session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}
		
		String loginID = null;
		String loginPW = null;
		Member member = null;
		System.out.println("== 로그인 ==");

		while (true) {

			System.out.printf("아이디 : ");
			loginID = sc.nextLine().trim();

			if (loginID.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}

			member = memberService.getMemberByLoginID(loginID);

			if (member == null) {
				System.out.println("존재하지 않는 아이디입니다.");
				continue;
			}
			break;
		}
		while (true) {

			System.out.printf("비밀번호 : ");
			loginPW = sc.nextLine().trim();

			if (loginPW.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}

			if (member.loginPW.equals(loginPW) == false) {
				System.out.println("입력하신 비밀번호가 올바르지 않습니다.");
				continue;
			}

			Session.login(member);
			System.out.printf("%s님 로그인 되었습니다.\n", Session.loginedMember.name);

			break;
		}
	}

	public void dologout() {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		System.out.printf("%s님 로그아웃 되었습니다.\n", Session.loginedMember.name);
		Session.logout();
	}

	public void showprofile() {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		System.out.println("== 마이페이지 ==");
		System.out.printf("아이디 : %s\n", Session.loginedMember.loginID);
		System.out.printf("이름 : %s\n", Session.loginedMember.name);
		System.out.printf("가입한 날짜 : %s\n", Util.datetimeFormat(Session.loginedMember.regDate));
	}
}
