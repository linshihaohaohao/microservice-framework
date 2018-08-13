package cn.com.bluemoon.demo.user.common.response;

import java.io.Serializable;

import cn.com.bluemoon.demo.common.domain.BaseResponse;

public class TokenGetResponse extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 8871328696585548402L;

	private String token;

	public TokenGetResponse() {
		super();
	}

	public TokenGetResponse(Boolean isSuccess, int responseCode, String responseMsg) {
		super(isSuccess, responseCode, responseMsg);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
