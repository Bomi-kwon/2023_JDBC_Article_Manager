package com.KoreaIT.example.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	
	public int id;
	public String title;
	public String body;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public int memberID;
	public String writername;
	public int viewCount;
	
	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.memberID = (int) articleMap.get("memberID");
		this.writername = (String) articleMap.get("writername");
		this.viewCount = (int) articleMap.get("viewCount");
	}

}
