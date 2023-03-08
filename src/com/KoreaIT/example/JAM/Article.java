package com.KoreaIT.example.JAM;

public class Article {
	
	public int id;
	public String title;
	public String body;
	String regDate;
	String updateDate;
	
	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	
	public Article(int id, String regDate, String updateDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
	}

}
