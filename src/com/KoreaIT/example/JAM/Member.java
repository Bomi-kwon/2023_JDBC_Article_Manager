package com.KoreaIT.example.JAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {

	public int id;
	public String loginID;
	public String loginPW;
	public String name;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	
	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.regDate = (LocalDateTime) memberMap.get("regDate");
		this.updateDate = (LocalDateTime) memberMap.get("updateDate");
		this.loginID = (String) memberMap.get("loginID");
		this.loginPW = (String) memberMap.get("loginPW");
		this.name = (String) memberMap.get("name");
	}
}
