package com.KoreaIT.example.JAM.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.Dao.ArticleDao;
import com.KoreaIT.example.JAM.util.DBUtil;

public class ArticleService {
	private ArticleDao articleDao;
	private Connection conn;

	public ArticleService(Connection conn) {
		this.conn = conn;
		this.articleDao = new ArticleDao(conn);
	}

	public int dowrite(String title, String body) {
		return articleDao.dowrite(title, body);
	}

	public int docount(int searchID) {
		return articleDao.docount(searchID);
	}

	public void domodify(String title, String body, int searchID) {
		articleDao.domodify(title, body, searchID);
	}

	public void dodelete(int searchID) {
		articleDao.dodelete(searchID);
	}

	public List<Article> getArticles() {

		List<Map<String, Object>> articleListMap = articleDao.getArticles();

		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	public Article getArticle(int searchID) {
		
		Map<String, Object> articleMap = articleDao.getArticle(searchID);
				
		Article article = new Article(articleMap);
		
		return article;
	}

}
