package cn.com.bluemoon.admin.domain.vo;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String account;
	
	private String realName;
	
	private String mobileNo;
	
	private String sex;
	
	private String blood;
	
	private String empType;

	public String getAccount() {
		return account==null?"":account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName==null?"":realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobileNo() {
		return mobileNo==null?"":mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSex() {
		return sex==null?"":sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBlood() {
		return blood==null?"":blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getEmpType() {
		return empType==null?"":empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	@Override
	public String toString() {
		return "UserInfo [account=" + account + ", realName=" + realName
				+ ", mobileNo=" + mobileNo + ", sex=" + sex + ", blood="
				+ blood + ", empType=" + empType + "]";
	}

	
}
