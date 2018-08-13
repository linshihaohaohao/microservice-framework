package cn.com.bluemoon.demo.common.domain;

import java.io.Serializable;

public class PageDto implements Serializable{

	private static final long serialVersionUID = -2309605798434179979L;

	private String offset;
	
	private String pageSize;
	
	private String sortOrder;

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
}
