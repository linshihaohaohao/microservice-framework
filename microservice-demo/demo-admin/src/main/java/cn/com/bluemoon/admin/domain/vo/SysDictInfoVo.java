package cn.com.bluemoon.admin.domain.vo;

public class SysDictInfoVo {
	
	private String dictID;
	private String dictName;
	
	public String getDictID() {
		return dictID;
	}
	public void setDictID(String dictID) {
		this.dictID = dictID;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	
	@Override
	public String toString() {
		return "MallSysDictInfo [dictID=" + dictID + ", dictName=" + dictName
				+ "]";
	}
	

}
