package com.KoreaIT.example.JAM.Dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleDao {
	Connection conn;
	List<Article> articles;

	public ArticleDao(Connection conn) {
		this.conn = conn;
		this.articles = new ArrayList<>();
	}

	public int dowrite(String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);

		return DBUtil.insert(conn, sql);
	}
	
	public int docount(int searchID) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", searchID);

		return DBUtil.selectRowIntValue(conn, sql);
	}

	public void domodify(String title, String body, int searchID) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", searchID);

		DBUtil.update(conn, sql);
	}

	public void dodelete(int searchID) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", searchID);

		DBUtil.delete(conn, sql);
	}
	
	public List<Map<String, Object>> getArticles() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> getArticle(int searchID) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", searchID);

		return DBUtil.selectRow(conn, sql);
	}

}
