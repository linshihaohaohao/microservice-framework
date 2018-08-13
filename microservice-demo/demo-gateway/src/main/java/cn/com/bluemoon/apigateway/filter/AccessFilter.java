package cn.com.bluemoon.apigateway.filter;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import cn.com.bluemoon.apigateway.vo.ApiLogVo;
import cn.com.bluemoon.utils.encrypt.MD5Implementor;
import cn.com.bluemoon.utils.string.StringUtil;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mijun on 2016/10/10.
 */
public class AccessFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

	/**
	 * 拦截器中请求数据密钥
	 */
	public static String SYS_AUTH_SECRECT = "Er78s1hcT4Tyoaj2";

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		// log.info(String.format("%s request to %s", request.getMethod(),
		// request.getRequestURL().toString()));
		// Object accessToken = request.getParameter("accessToken");
		// if(accessToken == null) {
		// log.warn("access token is empty");
		// ctx.setSendZuulResponse(false);
		// ctx.setResponseStatusCode(401);
		// return "access token is empty";
		// }
		// log.info("access token ok");
		
		//放行OPTIONS请求
		if (request.getMethod().equals(RequestMethod.OPTIONS.toString())){
			return null;
		}
		
		//提供给测试的后门标识
		String testPsw = request.getParameter("testPsw");
		if ("LHtSW4Kk".equals(testPsw)) {
			return null;
		}
		
		if((request.getRequestURI().indexOf("/wechat")>=0) || (request.getRequestURI().indexOf("/downloadApp")>=0)
				|| (request.getRequestURI().indexOf("Callback")>=0)) {
			return null;
		}

		String userIp = this.getIpAddr(request);

		String appType = request.getParameter("appType"); // app类型
		String client = request.getParameter("client"); // 客户端类型
														// 来源(如ios，Android、pc、wx)
		String cuid = request.getParameter("cuid"); // 设备号（mac地址）
		if (StringUtil.isEmpty(cuid) && "wx".equals(client)) {
			cuid = userIp;
		}
		String format = request.getParameter("format"); // 请求格式 json
		String time = request.getParameter("time"); // 请求时间
		String version = request.getParameter("version"); // 应用版本号
		String sign = request.getParameter("sign"); // 验证签名(sign=md5(
													// (parameter1+parameter2+parameter3+secret).toLowerCase())
		String url = request.getRequestURI();
//		log.info(userIp + ":" + url);

		if (StringUtil.isEmpty(appType) || StringUtil.isEmpty(client) || StringUtil.isEmpty(version) || StringUtil.isEmpty(cuid)
				|| StringUtil.isEmpty(format) || StringUtil.isEmpty(time) || StringUtil.isEmpty(sign)) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			return "common parameters is empty";
		} else {
			Map<String, String> sortMap = new TreeMap<String, String>();
			sortMap.put("client", client);
			sortMap.put("cuid", cuid);
			sortMap.put("format", format);
			sortMap.put("time", time);
			sortMap.put("version", version);

			String value = "";
			for (Map.Entry<String, String> entry : sortMap.entrySet()) {
				if (entry.getValue() != null) {
					value = value + entry.getValue();
				}
			}
			
			String bodyJsonStr = "";
			if (request.getRequestURI().indexOf("/uploadVideo") < 0) {
				try {
					BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
					bodyJsonStr = requestWrapper.getRequestPostStr((HttpServletRequest)requestWrapper);
				} catch (IOException e) {
					e.printStackTrace();
					ctx.setSendZuulResponse(false);
					ctx.setResponseStatusCode(401);
					return "common parameters is empty";
				}
			}
			
			
			String signSource = SYS_AUTH_SECRECT + value + bodyJsonStr + SYS_AUTH_SECRECT;
			
			String keySign = MD5Implementor.MD5Encode(signSource).toLowerCase();
			
			if (!keySign.equals(sign)) {
				
				log.info(keySign + "---------" + sign + ", sign error!");
				
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(402);
				return "sign is illegal!";
			}

			this.apiLogOut(request, bodyJsonStr);
		}

		return null;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	private void apiLogOut(HttpServletRequest request, String parameter) {
		String apiLogPatten = "apiLog - ";
		
		ApiLogVo vo = new ApiLogVo();
		vo.setAppType(request.getParameter("appType"));
		vo.setClient(request.getParameter("client"));
		vo.setCuid(request.getParameter("cuid"));
		vo.setIp(this.getIpAddr(request));
		vo.setGps(request.getParameter("lng"), request.getParameter("lat"), request.getParameter("hig"));
		if (StringUtil.isNotEmpty(parameter)) {
			vo.setParameter(JSONObject.fromObject(parameter));
		}	
		vo.setTimestamp(new Long(request.getParameter("time")));
		vo.setUri(request.getRequestURI());
		vo.setVersion(request.getParameter("version"));
		
		apiLogPatten += JSONObject.fromObject(vo).toString();
		
		log.info(apiLogPatten);
	}
}
