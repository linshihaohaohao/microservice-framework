package cn.com.bluemoon.utils.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static HttpResponseVo doMapPost(String url, Map<String, String> headerParams, Map<String, Object> params) {
		HttpResponseVo myResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			// 设置请求头
			if (null != headerParams) {
				Iterator iterator = headerParams.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					httpPost.setHeader(elem.getKey(), elem.getValue());
				}
			}

			// httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response != null) {

				myResponse = new HttpResponseVo();
				myResponse.setStatusCode(response.getStatusLine().getStatusCode());

				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					String result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
					myResponse.setResult(result);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myResponse;
	}

	public static HttpResponseVo doJsonPost(String url, Map<String, String> headerParams, JSONObject jsonobj) {
		HttpResponseVo myResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			// 设置请求头
			if (null != headerParams) {
				Iterator iterator = headerParams.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					httpPost.setHeader(elem.getKey(), elem.getValue());
				}
			}

			// 设置参数
			StringEntity stringEntity = new StringEntity(jsonobj.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response != null) {

				myResponse = new HttpResponseVo();
				myResponse.setStatusCode(response.getStatusLine().getStatusCode());

				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					String result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
					myResponse.setResult(result);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myResponse;
	}

	public static HttpResponseVo doMapPostSSL(String url, Map<String, String> headerParams, Map<String, String> params) {
		HttpResponseVo myResponse = null;
		try {
			HttpClient httpClient = new SSLClient();
			HttpPost httpPost = new HttpPost(url);

			// 设置请求头
			if (null != headerParams) {
				Iterator iterator = headerParams.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					httpPost.setHeader(elem.getKey(), elem.getValue());
				}
			}

			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {

				myResponse = new HttpResponseVo();
				myResponse.setStatusCode(response.getStatusLine().getStatusCode());

				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					String result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
					myResponse.setResult(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return myResponse;
	}

	public static HttpResponseVo doJsonPostSSL(String url, Map<String, String> headerParams, JSONObject jsonobj) {
		HttpResponseVo myResponse = null;
		try {
			HttpClient httpClient = new SSLClient();
			HttpPost httpPost = new HttpPost(url);

			// 设置请求头
			if (null != headerParams) {
				Iterator iterator = headerParams.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					httpPost.setHeader(elem.getKey(), elem.getValue());
				}
			}

			// 设置参数
			StringEntity stringEntity = new StringEntity(jsonobj.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {

				myResponse = new HttpResponseVo();
				myResponse.setStatusCode(response.getStatusLine().getStatusCode());

				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					String result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
					myResponse.setResult(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return myResponse;
	}
}
