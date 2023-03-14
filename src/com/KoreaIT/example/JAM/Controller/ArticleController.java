package com.KoreaIT.example.JAM.Controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleController {
	Connection conn;
	Scanner sc;

	public ArticleController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}

	public void dowrite() {
		System.out.println("== 게시물 작성 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);

		int id = DBUtil.insert(conn, sql);

		System.out.printf("%d번 글이 생성되었습니다.\n", id);
	}

	public void showlist() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}

		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다.");
			return;
		}

		System.out.println("== 게시물 목록 ==");
		System.out.println("번호	|	제목");
		for (Article article : articles) {
			System.out.printf("%d	|	%s\n", article.id, article.title);
		}
	}

	public void domodify(String cmd) {
		int searchID = Integer.parseInt(cmd.split(" ")[2]);

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", searchID);

		int articlesCount = DBUtil.selectRowIntValue(conn, sql);

		if (articlesCount == 0) {
			System.out.printf("%d번 글이 없습니다.\n", searchID);
			return;
		}

		System.out.println("== 게시물 수정 ==");
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", searchID);

		int id = DBUtil.update(conn, sql);

		System.out.printf("%d번 게시물이 수정되었습니다.\n", searchID);

	}

	public void dodelete(String cmd) {
		int searchID = Integer.parseInt(cmd.split(" ")[2]);

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", searchID);

		int articlesCount = DBUtil.selectRowIntValue(conn, sql);

		if (articlesCount == 0) {
			System.out.printf("%d번 글이 없습니다.\n", searchID);
			return;
		}

		System.out.println("== 게시물 삭제 ==");

		sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", searchID);

		int id = DBUtil.delete(conn, sql);

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", searchID);

	}

	public void showdetail(String cmd) {
		int searchID = Integer.parseInt(cmd.split(" ")[2]);
		
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", searchID);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		if (articleMap.isEmpty()) {
			System.out.printf("%d번 글이 없습니다.\n", searchID);
			return;
		}
		
		Article article = new Article(articleMap);

		System.out.println("== 게시물 보기 ==");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("수정날짜 : %s\n", article.regDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}

}
