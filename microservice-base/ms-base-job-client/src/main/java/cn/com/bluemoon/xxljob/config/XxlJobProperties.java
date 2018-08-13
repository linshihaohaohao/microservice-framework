package cn.com.bluemoon.xxljob.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xxlJob")
public class XxlJobProperties {

	private String registUrl;
	
	private String accessToken;

	public String getRegistUrl() {
		return registUrl;
	}

	public void setRegistUrl(String registUrl) {
		this.registUrl = registUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
