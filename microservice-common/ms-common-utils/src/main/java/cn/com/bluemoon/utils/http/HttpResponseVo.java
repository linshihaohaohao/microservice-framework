package cn.com.bluemoon.utils.http;

import java.io.Serializable;

public class HttpResponseVo implements Serializable{

	private static final long serialVersionUID = -9070541519228139209L;
	
	private int statusCode;
	
	private String result;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
