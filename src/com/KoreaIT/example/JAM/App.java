package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		int lastarticleId = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");

			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();

				if (cmd.equals("exit")) {
					break;
				}

				else if (cmd.equals("article write")) {
					System.out.println("== 게시물 작성 ==");
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String body = sc.nextLine();

					int id = lastarticleId + 1;

					try {
						String sql = "INSERT INTO article";
						sql += " SET regDate = NOW()";
						sql += ", updateDate = NOW()";
						sql += ", title = '" + title + "'";
						sql += ", `body` = '" + body + "'";

						pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					}

					catch (SQLException e) {
						System.out.println("에러: " + e);
					}

					lastarticleId++;

					System.out.printf("%d번 글이 생성되었습니다.\n", id);
				}

				else if (cmd.startsWith("article modify ")) {
					int searchID = Integer.parseInt(cmd.split(" ")[2]);

					System.out.println("== 게시물 수정 ==");
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String body = sc.nextLine();

					try {
						String sql = "UPDATE article";
						sql += " SET updateDate = NOW()";
						sql += ", title = '" + title + "'";
						sql += ", `body` = '" + body + "'";
						sql += " WHERE id = " + searchID + ";";

						pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();

					} catch (SQLException e) {
						System.out.println("에러: " + e);
					}

					System.out.printf("%d번 게시물이 수정되었습니다.\n", searchID);

				}

				else if (cmd.equals("article list")) {

					ResultSet rs = null;

					List<Article> articles = new ArrayList<>();

					try {
						String sql = "SELECT *";
						sql += " FROM article";
						sql += " ORDER BY id DESC;";

						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();

						while (rs.next()) {
							int id = rs.getInt("id");
							String regDate = rs.getString("regDate");
							String updateDate = rs.getString("updateDate");
							String title = rs.getString("title");
							String body = rs.getString("body");

							Article article = new Article(id, regDate, updateDate, title, body);
							articles.add(article);
						}

					} catch (SQLException e) {
						System.out.println("에러: " + e);
					} finally {
						try {
							if (rs != null && !rs.isClosed()) {
								rs.close();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					if (articles.size() == 0) {
						System.out.println("게시물이 없습니다.");
						continue;
					}

					System.out.println("== 게시물 목록 ==");
					System.out.println("번호	|	제목");
					for (Article article : articles) {
						System.out.printf("%d	|	%s\n", article.id, article.title);
					}
				}

				else {
					System.out.println("존재하지 않는 명령어 입니다.");
				}
			}
		}
		catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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