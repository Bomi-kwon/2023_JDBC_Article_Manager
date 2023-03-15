package com.KoreaIT.example.JAM.Controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.Service.ArticleService;

public class ArticleController {
	private Scanner sc;
	private ArticleService articleService;

	public ArticleController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.articleService = new ArticleService(conn);
	}

	public void dowrite() {
		System.out.println("== 게시물 작성 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int id = articleService.dowrite(title, body);

		System.out.printf("%d번 글이 생성되었습니다.\n", id);
	}

	public void domodify(String cmd) {
		int searchID = Integer.parseInt(cmd.split(" ")[2]);

		int articlesCount = articleService.docount(searchID);

		if (articlesCount == 0) {
			System.out.printf("%d번 글이 없습니다.\n", searchID);
			return;
		}

		System.out.println("== 게시물 수정 ==");
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		articleService.domodify(title, body, searchID);

		System.out.printf("%d번 게시물이 수정되었습니다.\n", searchID);
	}

	public void dodelete(String cmd) {
		int searchID = Integer.parseInt(cmd.split(" ")[2]);

		int articlesCount = articleService.docount(searchID);

		if (articlesCount == 0) {
			System.out.printf("%d번 글이 없습니다.\n", searchID);
			return;
		}

		System.out.println("== 게시물 삭제 ==");

		articleService.dodelete(searchID);

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", searchID);
	}

	public void showlist() {

		List<Article> articles = articleService.getArticles();

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

	public void showdetail(String cmd) {
		int searchID = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.getArticle(searchID);
		
		if (article == null) {
			System.out.printf("%d번 글이 없습니다.\n", searchID);
			return;
		}

		System.out.println("== 게시물 보기 ==");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}
}
