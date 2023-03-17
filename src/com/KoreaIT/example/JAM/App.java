package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Controller.ArticleController;
import com.KoreaIT.example.JAM.Controller.MemberController;

public class App {
	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			
			ArticleController articlecontroller = new ArticleController(conn, sc);
			MemberController membercontroller = new MemberController(conn, sc);

			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();

				if (cmd.equals("exit")) {
					break;
				}
				
				else if (cmd.equals("member join")) {
					membercontroller.dojoin();
				}
				
				else if (cmd.equals("member login")) {
					membercontroller.dologin();
				}
				
				else if (cmd.equals("member logout")) {
					membercontroller.dologout();
				}
				
				else if (cmd.equals("member profile")) {
					membercontroller.showprofile();
				}

				else if (cmd.equals("article write")) {
					articlecontroller.dowrite();
				}

				else if (cmd.startsWith("article list")) {
					articlecontroller.showlist(cmd);
				}

				else if (cmd.startsWith("article modify ")) {
					articlecontroller.domodify(cmd);
				}

				else if (cmd.startsWith("article delete ")) {
					articlecontroller.dodelete(cmd);
				}

				else if (cmd.startsWith("article detail ")) {
					articlecontroller.showdetail(cmd);
				}

				else {
					System.out.println("존재하지 않는 명령어 입니다.");
					continue;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		sc.close();
		System.out.println("== 프로그램 끝 ==");
	}

}
