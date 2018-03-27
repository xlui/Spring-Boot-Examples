package me.xlui.spring.entity;


import me.xlui.spring.enums.SexEnum;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionID = 1L;

	private Long id;
	private String username;
	private String password;
	private SexEnum userSex;    // 特别的，将 userSex 和 nickName 表示为与数据库字段不同的格式
	private String nickName;

	@Override
	public String toString() {
		return "username " + this.username + ", password " + this.password + ", sex " + this.userSex.name();
	}

	public User() {
		super();
	}

	public User(String username, String password, SexEnum userSex) {
		this.username = username;
		this.password = password;
		this.userSex = userSex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SexEnum getUserSex() {
		return userSex;
	}

	public void setUserSex(SexEnum userSex) {
		this.userSex = userSex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
