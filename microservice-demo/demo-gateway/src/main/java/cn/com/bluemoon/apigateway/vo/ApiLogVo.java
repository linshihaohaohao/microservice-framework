package cn.com.bluemoon.apigateway.vo;

import net.sf.json.JSONObject;

public class ApiLogVo {

	private String appType;
	
	private String client;
	
	private String uri;
	
	private String user;
	
	private String ip;
	
	private String cuid;
	
	private Long timestamp;
	
	private String version;
	
	private String gps;
	
	private JSONObject parameter;

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public JSONObject getParameter() {
		return parameter;
	}

	public void setParameter(JSONObject parameter) {
		this.parameter = parameter;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String lng, String lat, String hig) {
		this.gps = lng + "," + lat + "," + hig;
	}	
}
