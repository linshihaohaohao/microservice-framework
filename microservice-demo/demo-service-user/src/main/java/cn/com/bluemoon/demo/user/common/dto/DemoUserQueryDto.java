package cn.com.bluemoon.demo.user.common.dto;

import cn.com.bluemoon.demo.common.domain.PageDto;


public class DemoUserQueryDto extends PageDto{

	private static final long serialVersionUID = 822414931579755909L;
	
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
