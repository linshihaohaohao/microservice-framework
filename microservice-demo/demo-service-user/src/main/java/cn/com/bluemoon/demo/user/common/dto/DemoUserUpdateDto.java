package cn.com.bluemoon.demo.user.common.dto;

import java.io.Serializable;

public class DemoUserUpdateDto implements Serializable{

	private static final long serialVersionUID = -6772067056578073611L;
	
	private Long id;
	private String password;
	private String introduction;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
