package me.xlui.spring;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document
// `@Document` 注解映射领域模型和 MongoDB 的文档
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
//	表明这个属性为文档的 ID
	private String id;
	private String username;
	@Field("Password")
//	表明这个属性在文档中的名称为 Password
	private String password;

	public User() {
		super();
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
}
