package cn.com.bluemoon.demo.common.domain;

import java.io.Serializable;

/**
 * 通用响应model
 * @author xj.z
 * @version 1.0
 */
public class BaseResponse implements Serializable{

	private static final long serialVersionUID = -6686048184626557273L;
	
	private Boolean isSuccess = true;
	private int responseCode = 0;
	private String responseMsg = "请求成功";
	
	public BaseResponse(){}
	
	public BaseResponse(Boolean isSuccess, int responseCode,
			String responseMsg) {
		this.isSuccess = isSuccess;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}
